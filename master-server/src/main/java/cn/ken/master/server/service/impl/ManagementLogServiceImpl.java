package cn.ken.master.server.service.impl;

import cn.ken.master.core.constant.Delimiter;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.core.util.StringUtil;
import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.mapper.ManagementLogMapper;
import cn.ken.master.server.model.management.log.ManagementLogDTO;
import cn.ken.master.server.model.management.log.ManagementLogRequest;
import cn.ken.master.server.service.ManagementLogService;
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

    @Override
    public int insert(ManagementLogDO managementLogDO) {
        return managementLogMapper.insert(managementLogDO);
    }

    @Override
    public Result<List<ManagementLogDO>> selectByCondition(ManagementLogRequest request) {
        if (request.getAppId() == null) {
            return Result.error("appId不能为空");
        }
        ManagementLogDTO managementLogDTO = ManagementLogDTO.of(request);
        Set<String> machineSet = StringUtil.split(request.getMachines(), Delimiter.COMMA, Function.identity());
        managementLogDTO.setMachineSet(machineSet);
        List<ManagementLogDO> managementLogDOS = managementLogMapper.selectByCondition(managementLogDTO);
        return Result.success(managementLogDOS);
    }


}
