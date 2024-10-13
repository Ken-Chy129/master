package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.NamespaceDO;

import java.util.List;

public interface NamespaceService {

    void insert(NamespaceDO namespaceDO);

    Result<List<NamespaceDO>> selectAll();
}
