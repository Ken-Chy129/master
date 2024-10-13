package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.FieldDO;

import java.util.List;

public interface FieldService {

    void insert(FieldDO fieldDO);

    Result<List<FieldDO>> selectAll();
}
