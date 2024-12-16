package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.constant.Delimiter;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.util.StringUtil;
import cn.ken.master.server.management.mapper.NamespaceMapper;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.mapper.ManagementLogMapper;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.model.management.log.ManagementLogQuery;
import cn.ken.master.server.management.model.management.log.ManagementLogRequest;
import cn.ken.master.server.management.service.ManagementLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
public class ManagementLogServiceImpl implements ManagementLogService {

    @Resource
    private ManagementLogMapper managementLogMapper;

    @Resource
    private NamespaceMapper namespaceMapper;

    @Override
    public int insert(ManagementLogDO managementLogDO) {
        return managementLogMapper.insert(managementLogDO);
    }

    @Override
    public PageResult<List<ManagementLogDO>> selectByCondition(ManagementLogRequest request) {
        if (request.getAppId() == null) {
            return PageResult.buildError("appId不能为空");
        }
        ManagementLogQuery managementLogQuery = ManagementLogQuery.of(request);
        Long namespaceId = request.getNamespaceId();
        NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
        if (namespaceDO != null) {
            managementLogQuery.setNamespace(namespaceDO.getName());
        }
        Set<String> machineSet = StringUtil.split(request.getMachines(), Delimiter.COMMA, Function.identity());
        managementLogQuery.setMachineSet(machineSet);
        Long count = managementLogMapper.count(managementLogQuery);
        if (count == 0) {
            return PageResult.buildSuccess();
        }
        List<ManagementLogDO> managementLogDOS = managementLogMapper.selectByCondition(managementLogQuery);
        PageResult<List<ManagementLogDO>> result = PageResult.buildSuccess(managementLogDOS);
        result.setTotal(count);
        return result;
    }


}
