package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.enums.PushTypeEnum;
import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.ManagementDTO;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Pair;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.core.ManagementClient;
import cn.ken.master.server.management.mapper.*;
import cn.ken.master.server.management.model.entity.*;
import cn.ken.master.server.management.model.management.field.*;
import cn.ken.master.server.management.service.FieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Slf4j
@Service
public class FieldServiceImpl implements FieldService {

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private NamespaceMapper namespaceMapper;

    @Resource
    private MachineMapper machineMapper;

    @Resource
    private ManagementLogMapper managementLogMapper;

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private TemplateFieldMapper templateFieldMapper;

    @Resource
    private ManagementClient managementClient;

    @Override
    public Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq) {
        FieldDO fieldDO = fieldMapper.selectById(fieldPushReq.getFieldId());
        Long appId = fieldDO.getAppId();
        List<Pair<String, Integer>> machineList;
        String pushType = fieldPushReq.getPushType();
        if (PushTypeEnum.SPECIFIC.name().equalsIgnoreCase(pushType)) {
            machineList = Arrays.stream(fieldPushReq.getMachines().split(",")).map(machine -> {
                String[] split = machine.split(":");
                return new Pair<>(split[0], Integer.parseInt(split[1]));
            }).toList();
        } else {
            machineList = machineMapper.selectByAppId(appId).stream()
                    .map(machineDO -> {
                        String ip = machineDO.getIpAddress();
                        Integer port = machineDO.getPort();
                        return new Pair<>(ip, port);
                    }).toList();
        }
        // 应用启动时首先向服务端注册应用，并上报变量初始值以及提供的接口的端口号，之后双方建立长连接定期发送心跳包以维持机器状态
        // 服务端在启动时便初始化Socket接收应用注册消息，每次有应用注册的时候便保存到数据库中，之后当控制台发起变更消息时则会找到ip地址和端口号去进行建立连接发送http请求进行修改
        // 应用启动时可以选择是否通过持久化值进行更新当前的变量值
        for (Pair<String, Integer> machine : machineList) {
            String ipAddress = machine.getLeft();
            Integer port = machine.getRight();
            String oldValue = managementClient.putFieldValue(ipAddress, port, fieldPushReq.getNamespace(), fieldDO.getName(), fieldPushReq.getValue());
            ManagementLogDO managementLogDO = new ManagementLogDO();
            managementLogDO.setAppId(appId);
            managementLogDO.setNamespace(fieldPushReq.getNamespace());
            managementLogDO.setFieldId(fieldDO.getId());
            managementLogDO.setFieldName(fieldDO.getName());
            managementLogDO.setMachine(MessageFormat.format("{0}:{1}", ipAddress, port));
            managementLogDO.setBeforeValue(oldValue);
            managementLogDO.setAfterValue(fieldPushReq.getValue());
            managementLogDO.setStatus(1);
            managementLogDO.setPushType(1);
            managementLogDO.setModifier("颜洵");
            managementLogMapper.insert(managementLogDO);
        }

        if (fieldPushReq.getIsUpdateTemplate()) {
            // 更新默认模板的值
            templateFieldMapper.updateAppDefaultTemplateField(appId, fieldPushReq.getValue());
        }
        return Result.buildSuccess();
    }

    @Override
    public void registerField(Long appId, List<ManagementDTO> managementDTOList) {
        LambdaQueryWrapper<NamespaceDO> namespaceQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<FieldDO> fieldQueryWrapper = new LambdaQueryWrapper<>();
        for (ManagementDTO managementDTO : managementDTOList) {
            // 1. 找到对应的namespaceId，如果不存在则新增
            namespaceQueryWrapper.eq(NamespaceDO::getAppId, appId)
                            .eq(NamespaceDO::getClassName, managementDTO.getClassName());
            NamespaceDO namespaceDO = namespaceMapper.selectOne(namespaceQueryWrapper);
            if (namespaceDO == null) {
                namespaceDO = new NamespaceDO();
                namespaceDO.setAppId(appId);
                namespaceDO.setClassName(managementDTO.getClassName());
            } else {
                Long id = namespaceDO.getId();
                namespaceDO = new NamespaceDO();
                namespaceDO.setId(id);
            }
            namespaceDO.setName(managementDTO.getNamespace());
            namespaceDO.setDescription(managementDTO.getDesc());
            namespaceMapper.insertOrUpdate(namespaceDO);
            Long namespaceId = namespaceDO.getId();
            if (namespaceId == null) {
                NamespaceDO newNamespaceDO = namespaceMapper.selectOne(namespaceQueryWrapper);
                if (newNamespaceDO == null) {
                    continue;
                }
                namespaceId = newNamespaceDO.getId();
            }
            namespaceQueryWrapper.clear();
            // 2. 插入或更新命名空间下的变量
            List<ManageableFieldDTO> manageableFieldList = managementDTO.getManageableFieldList();
            for (ManageableFieldDTO field : manageableFieldList) {
                fieldQueryWrapper.eq(FieldDO::getAppId, appId)
                        .eq(FieldDO::getNamespaceId, namespaceId)
                        .eq(FieldDO::getName, field.getName());
                FieldDO fieldDO = fieldMapper.selectOne(fieldQueryWrapper);
                boolean isInsert = false;
                if (fieldDO == null) {
                    isInsert = true;
                    fieldDO = new FieldDO();
                    fieldDO.setAppId(appId);
                    fieldDO.setNamespaceId(namespaceId);
                    fieldDO.setName(field.getName());
                } else {
                    Long id = fieldDO.getId();
                    fieldDO = new FieldDO();
                    fieldDO.setId(id);
                }
                fieldDO.setDescription(field.getDesc());
                fieldMapper.insertOrUpdate(fieldDO);

                if (isInsert) {
                    // 对于初次创建的字段，将值写到默认模板中
                    FieldDO newFieldDO = fieldMapper.selectOne(fieldQueryWrapper);
                    Long fieldId = newFieldDO.getId();
                    Long templateId = templateMapper.selectAppDefaultTemplateId(appId);
                    TemplateFieldDO templateFieldDO = new TemplateFieldDO();
                    templateFieldDO.setFieldId(fieldId);
                    templateFieldDO.setFieldName(field.getName());
                    templateFieldDO.setNamespace(managementDTO.getNamespace());
                    templateFieldDO.setFieldValue(field.getValue());
                    templateFieldDO.setTemplateId(templateId);
                    templateFieldMapper.insert(templateFieldDO);
                }

                fieldQueryWrapper.clear();
            }
        }
    }

    @Override
    public Result<FieldVO> getFieldValue(Long fieldId) {
        FieldDO fieldDO = fieldMapper.selectById(fieldId);
        Long namespaceId = fieldDO.getNamespaceId();
        NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
        Long appId = fieldDO.getAppId();
        LambdaQueryWrapper<MachineDO> machineDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        machineDOLambdaQueryWrapper.eq(MachineDO::getAppId, appId)
                        .eq(MachineDO::getStatus, 1);
        List<MachineDO> machineDOS = machineMapper.selectList(machineDOLambdaQueryWrapper);
        FieldVO fieldVO = new FieldVO();
        fieldVO.setNamespace(namespaceDO.getName());
        fieldVO.setClassName(namespaceDO.getClassName());
        fieldVO.setDescription(fieldDO.getDescription());
        fieldVO.setName(fieldDO.getName());
        Map<String, String> machineValueMap = new HashMap<>();
        for (MachineDO machineDO : machineDOS) {
            String ipAddress = machineDO.getIpAddress();
            Integer port = machineDO.getPort();
            String value = managementClient.queryFieldValue(ipAddress, port, fieldVO.getNamespace(), fieldVO.getName());
            machineValueMap.put(ipAddress + ":" + port, value);
        }
        fieldVO.setMachineValueMap(machineValueMap);
        return Result.buildSuccess(fieldVO);
    }

    @Override
    public Result<List<ManagementFieldDTO>> selectByNamespaceId(String namespaceId) {
        List<ManagementFieldDTO> managementFieldDTOS = fieldMapper.selectByNamespaceId(namespaceId);
        return Result.buildSuccess(managementFieldDTOS);
    }

    @Override
    public PageResult<List<ManagementFieldDTO>> selectByCondition(ManagementFieldRequest request) {
        if (request.getAppId() == null) {
            return PageResult.buildError("appId不能为空");
        }
        ManagementFieldQuery managementFieldQuery = ManagementFieldQuery.of(request);
        Long count = fieldMapper.count(managementFieldQuery);
        if (count == 0) {
            return PageResult.buildSuccess();
        }
        List<ManagementFieldDTO> fieldDOS = fieldMapper.selectByCondition(managementFieldQuery);
        PageResult<List<ManagementFieldDTO>> result = PageResult.buildSuccess(fieldDOS);
        result.setTotal(count);
        return result;
    }
}
