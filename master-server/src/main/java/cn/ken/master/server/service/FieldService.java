package cn.ken.master.server.service;

import cn.ken.master.core.model.Namespace;
import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.model.field.FieldPushReq;

import java.util.List;

public interface FieldService {

    void insert(FieldDO fieldDO);

    Result<List<FieldDO>> selectByNamespaceId(Long namespaceId);

    Result<List<FieldDO>> selectByAppId(Long appId);

    Result<Boolean> pushFieldValue(FieldPushReq fieldPushReq);

    Result<Boolean> registerField(Long appId, List<Namespace> namespaceList);
}
