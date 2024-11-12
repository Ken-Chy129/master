package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.RecordDO;
import cn.ken.master.server.model.management.log.ManagementLogRequest;

import java.util.List;

public interface RecordService {

    int insert(RecordDO recordDO);

    Result<List<RecordDO>> selectByCondition(ManagementLogRequest request);

}
