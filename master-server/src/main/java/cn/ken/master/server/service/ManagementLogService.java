package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.model.management.log.ManagementLogRequest;

import java.util.List;

public interface ManagementLogService {

    int insert(ManagementLogDO managementLogDO);

    Result<List<ManagementLogDO>> selectByCondition(ManagementLogRequest request);

}
