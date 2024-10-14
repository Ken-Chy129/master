package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.FieldDO;

import java.util.List;

public interface FieldService {

    void insert(FieldDO fieldDO);

    Result<List<FieldDO>> selectByNamespaceId(Long namespaceId);

    Result<List<FieldDO>> selectByAppId(Long appId);
}
