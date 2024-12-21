package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.constant.Delimiter;
import cn.ken.master.server.common.enums.MachineTypeEnum;
import cn.ken.master.core.model.common.Pair;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.constant.ManagementConstant;
import cn.ken.master.server.common.enums.PushTypeEnum;
import cn.ken.master.server.core.AuthContext;
import cn.ken.master.server.core.ManagementClient;
import cn.ken.master.server.management.mapper.MachineMapper;
import cn.ken.master.server.management.mapper.ManagementLogMapper;
import cn.ken.master.server.management.mapper.TemplateFieldMapper;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.mapper.TemplateMapper;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;
import cn.ken.master.server.management.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private TemplateFieldMapper templateFieldMapper;

    @Resource
    private MachineMapper machineMapper;

    @Resource
    private ManagementLogMapper managementLogMapper;

    @Resource
    private ManagementClient managementClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Long> insert(TemplateFieldRequest request) {
        TemplateDO templateDO = new TemplateDO();
        templateDO.setAppId(AuthContext.getAppId());
        templateDO.setName(request.getName());
        templateDO.setDescription(request.getDescription());
        templateMapper.insert(templateDO);

        // 复制源
        Long fromTemplateId = request.getFromTemplateId();
        if (fromTemplateId == null) {
            return Result.buildSuccess(templateDO.getId());
        }

        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateId(fromTemplateId);
        for (TemplateFieldDO templateFieldDO : templateFieldDOList) {
            templateFieldDO.setId(null);
            templateFieldDO.setTemplateId(templateDO.getId());
        }
        templateFieldMapper.insert(templateFieldDOList);
        return Result.buildSuccess(templateDO.getId());
    }

    @Override
    public List<TemplateDO> selectAll() {
        return List.of();
    }

    @Override
    public Result<List<TemplateDO>> selectByAppId(Long appId) {
        return Result.buildSuccess(templateMapper.selectTemplateListByAppId(appId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> deleteById(Long templateId) {
        templateMapper.deleteById(templateId);
        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateId(templateId);
        List<Long> idList = templateFieldDOList.stream().map(TemplateFieldDO::getId).toList();
        templateFieldMapper.deleteByIds(idList);
        return Result.buildSuccess(true);
    }

    @Override
    public Result<Boolean> push(TemplateFieldRequest request) {
        Long appId = AuthContext.getAppId();
        // 1.查询模板字段值
        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateId(request.getFromTemplateId());
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
        // 3.推送
        for (Pair<String, Integer> machine : machineList) {
            String ipAddress = machine.getLeft();
            Integer port = machine.getRight();
            for (TemplateFieldDO templateFieldDO : templateFieldDOList) {
                String fieldName = templateFieldDO.getFieldName();
                String namespace = templateFieldDO.getNamespace();
                String fieldValue = templateFieldDO.getFieldValue();
                managementClient.putFieldValue(ipAddress, port, namespace, fieldName, fieldValue);
            }
            ManagementLogDO managementLogDO = new ManagementLogDO();
            managementLogDO.setAppId(appId);
            managementLogDO.setTemplateId(request.getFromTemplateId());
            managementLogDO.setMachine(ipAddress + Delimiter.COLON + port);
            managementLogDO.setStatus(1);
            managementLogDO.setPushType(PushTypeEnum.TEMPLATE.getCode());
            managementLogDO.setModifier("颜洵");
            managementLogMapper.insert(managementLogDO);
        }

        return Result.buildSuccess(true);
    }
}
