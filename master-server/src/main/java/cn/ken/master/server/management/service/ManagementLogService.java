package cn.ken.master.server.management.service;

import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.model.management.log.ManagementLogRequest;

import java.util.List;

public interface ManagementLogService {

    int insert(ManagementLogDO managementLogDO);

    PageResult<List<ManagementLogDO>> selectByCondition(ManagementLogRequest request);

}
