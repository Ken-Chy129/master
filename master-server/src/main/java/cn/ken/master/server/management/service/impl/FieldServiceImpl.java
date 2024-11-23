package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.enums.PushTypeEnum;
import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.ManagementDTO;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.core.ManagementClient;
import cn.ken.master.server.management.model.management.field.*;
import cn.ken.master.server.management.mapper.MachineMapper;
import cn.ken.master.server.management.mapper.NamespaceMapper;
import cn.ken.master.server.management.mapper.ManagementLogMapper;
import cn.ken.master.server.management.model.entity.FieldDO;
import cn.ken.master.server.management.mapper.FieldMapper;
import cn.ken.master.server.management.model.entity.MachineDO;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.service.FieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private ManagementClient managementClient;

    @Override
    public Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq) {
        FieldDO fieldDO = fieldMapper.selectById(fieldPushReq.getFieldId());
        Long appId = fieldDO.getAppId();
        List<MachineDO> machineDOList = machineMapper.selectByAppId(appId);
        String pushType = fieldPushReq.getPushType();
        if (PushTypeEnum.SPECIFIC.name().equalsIgnoreCase(pushType)) {
            Set<Long> requestMachineIdSet = Arrays.stream(fieldPushReq.getMachineIds().split(",")).map(Long::valueOf).collect(Collectors.toSet());
            machineDOList.removeIf(machineDO -> !requestMachineIdSet.contains(machineDO.getId()));
        }
        // todo: 此处socket不应该是保存在map中，即服务端不能一直维持socket连接，而是每次需要发送请求的时候再去建立连接，否则会导致当注册的应用和机器过多时长时间维持着非常多的socket
        // 应用启动时首先向服务端注册应用，并上报变量初始值以及提供的接口的端口号，之后双方建立长连接定期发送心跳包以维持机器状态
        // 服务端在启动时便初始化Socket接收应用注册消息，每次有应用注册的时候便保存到数据库中，之后当控制台发起变更消息时则会找到ip地址和端口号去进行建立连接发送http请求进行修改
        // 应用启动时可以选择是否通过持久化值进行更新当前的变量值
        for (MachineDO machineDO : machineDOList) {
            String ipAddress = machineDO.getIpAddress();
            Integer port = machineDO.getPort();
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
                if (fieldDO == null) {
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
    public Result<List<ManagementFieldDTO>> selectByCondition(ManagementFieldRequest request) {
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
