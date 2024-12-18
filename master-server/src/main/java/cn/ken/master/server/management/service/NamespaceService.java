package cn.ken.master.server.management.service;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.NamespaceDO;

import java.util.List;

public interface NamespaceService {

    void insert(NamespaceDO namespaceDO);

    Result<List<NamespaceDO>> selectByAppId(Long appId);
}
