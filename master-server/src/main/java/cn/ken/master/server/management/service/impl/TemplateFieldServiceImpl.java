package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.constant.Delimiter;
import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Pair;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.app.mapper.MachineMapper;
import cn.ken.master.server.common.enums.MachineTypeEnum;
import cn.ken.master.server.common.enums.PushStatusEnum;
import cn.ken.master.server.common.enums.PushTypeEnum;
import cn.ken.master.server.core.AuthContext;
import cn.ken.master.server.core.ManagementClient;
import cn.ken.master.server.management.mapper.*;
import cn.ken.master.server.management.model.entity.FieldDO;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldQuery;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;
import cn.ken.master.server.management.service.TemplateFieldService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TemplateFieldServiceImpl implements TemplateFieldService {

    @Resource
    private TemplateMapper templateMapper;
    @Resource
    private TemplateFieldMapper templateFieldMapper;
    @Resource
    private NamespaceMapper namespaceMapper;
    @Resource
    private FieldMapper fieldMapper;
    @Resource
    private ManagementClient managementClient;
    @Resource
    private ManagementLogMapper managementLogMapper;
    @Resource
    private MachineMapper machineMapper;

    @Override
    public int insert(TemplateFieldDO templateFieldDO) {
        System.out.println(templateFieldDO);
        return templateFieldMapper.insert(templateFieldDO);
    }

    @Override
    public List<TemplateFieldDO> selectAll() {
        return List.of();
    }

    @Override
    public List<ManageableFieldDTO> getTemplateFields(Long appId, String templateName) {
        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateName(appId, templateName);
        if (CollectionUtils.isEmpty(templateFieldDOList)) {
            return Collections.emptyList();
        }
        return templateFieldDOList.stream()
                .map(templateFieldDO -> {
                    ManageableFieldDTO manageableFieldDTO = new ManageableFieldDTO();
                    manageableFieldDTO.setNamespace(templateFieldDO.getNamespace());
                    manageableFieldDTO.setName(templateFieldDO.getFieldName());
                    manageableFieldDTO.setValue(templateFieldDO.getFieldValue());
                    return manageableFieldDTO;
                })
                .toList();
    }

    @Override
    public PageResult<List<TemplateFieldDO>> selectFieldByCondition(TemplateFieldRequest request) {
        System.out.println(request);
        TemplateFieldQuery templateFieldQuery = TemplateFieldQuery.of(request);
        Long namespaceId = request.getNamespaceId();
        if (namespaceId != null && request.getNamespace() == null) {
            NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
            templateFieldQuery.setNamespace(namespaceDO.getName());
        }
        Long count = templateFieldMapper.count(templateFieldQuery);
        if (count == 0) {
            return PageResult.buildSuccess();
        }
        List<TemplateFieldDO> templateFieldDOS = templateFieldMapper.selectByCondition(templateFieldQuery);
        PageResult<List<TemplateFieldDO>> result = PageResult.buildSuccess(templateFieldDOS);
        result.setPageCount(count);
        return result;
    }

    @Override
    public Result<Boolean> updateField(TemplateFieldRequest request) {
        templateFieldMapper.updateFieldValueById(request.getTemplateFieldId(), request.getFieldValue());
        return Result.buildSuccess(true);
    }

    @Override
    public Result<Boolean> addField(TemplateFieldRequest request) {
        TemplateFieldQuery templateFieldQuery = new TemplateFieldQuery();
        templateFieldQuery.setFieldId(request.getFieldId());
        templateFieldQuery.setTemplateId(request.getTemplateId());
        Long count = templateFieldMapper.count(templateFieldQuery);
        if (count > 0) {
            return Result.buildError("模板中已存在该字段值");
        }

        FieldDO fieldDO = fieldMapper.selectById(request.getFieldId());
        if (fieldDO == null) {
            return Result.buildError("查询不到对应的字段");
        }

        Long namespaceId = fieldDO.getNamespaceId();
        NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
        if (namespaceDO == null) {
            return Result.buildError("查询不到对应的命名空间");
        }

        TemplateFieldDO templateFieldDO = new TemplateFieldDO();
        templateFieldDO.setTemplateId(request.getTemplateId());
        templateFieldDO.setFieldId(request.getFieldId());
        templateFieldDO.setNamespace(namespaceDO.getName());
        templateFieldDO.setFieldName(fieldDO.getName());
        templateFieldDO.setFieldValue(request.getFieldValue());
        templateFieldMapper.insert(templateFieldDO);
        return Result.buildSuccess(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> pushField(TemplateFieldRequest request) {
        // 1.查询模板字段值
        Long appId = AuthContext.getAppId();
        Long templateFieldId = request.getTemplateFieldId();
        TemplateFieldDO templateFieldDO = templateFieldMapper.selectById(templateFieldId);
        // 2.查询机器
        List<Pair<String, Integer>> machineList;
        String machineType = request.getMachineType();
        if (MachineTypeEnum.SPECIFIC.name().equalsIgnoreCase(machineType)) {
            machineList = Arrays.stream(request.getMachines().split(Delimiter.COMMA)).map(machine -> {
                String[] split = machine.split(Delimiter.COLON);
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
        log.info("推送的目标机器:{}", machineList.stream().map(pair -> pair.getLeft() + Delimiter.COLON + pair.getRight()).collect(Collectors.toList()));
        // 3.推送
        for (Pair<String, Integer> machine : machineList) {
            String ipAddress = machine.getLeft();
            Integer port = machine.getRight();
            Result<String> result = managementClient.putFieldValue(ipAddress, port, templateFieldDO.getNamespace(), templateFieldDO.getFieldName(), templateFieldDO.getFieldValue());
            ManagementLogDO managementLogDO = new ManagementLogDO();
            if (result.isSuccess()) {
                managementLogDO.setStatus(PushStatusEnum.SUCCESS.getValue());
                managementLogDO.setBeforeValue(result.getData());
            } else {
                managementLogDO.setStatus(PushStatusEnum.FAIL.getValue());
                managementLogDO.setDetailMsg(result.getData());
            }
            managementLogDO.setAppId(appId);
            managementLogDO.setNamespace(templateFieldDO.getNamespace());
            managementLogDO.setFieldId(templateFieldDO.getFieldId());
            managementLogDO.setFieldName(templateFieldDO.getFieldName());
            managementLogDO.setMachine(ipAddress + Delimiter.COLON + port);
            managementLogDO.setAfterValue(templateFieldDO.getFieldValue());
            managementLogDO.setTemplateId(templateFieldDO.getTemplateId());
            managementLogDO.setPushType(PushTypeEnum.FIELD.getValue());
            managementLogDO.setModifier("颜洵");
            managementLogMapper.insert(managementLogDO);
        }

        return Result.buildSuccess(true);
    }

    @Override
    public Result<Boolean> deleteById(Long templateFieldId) {
        templateFieldMapper.deleteById(templateFieldId);
        return Result.buildSuccess(true);
    }

}
