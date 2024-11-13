package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.mapper.ManagementLogMapper;
import cn.ken.master.server.model.management.log.ManagementLogRequest;
import cn.ken.master.server.service.ManagementLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }


}
