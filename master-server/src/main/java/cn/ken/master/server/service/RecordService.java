package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.RecordDO;

import java.util.List;

public interface RecordService {

    int insert(RecordDO recordDO);

    List<RecordDO> selectAll();

    Result<List<RecordDO>> selectByFieldId(Long fieldId);
}
