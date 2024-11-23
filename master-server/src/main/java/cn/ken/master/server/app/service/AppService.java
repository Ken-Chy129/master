package cn.ken.master.server.app.service;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.AppDO;

import java.util.List;

public interface AppService {

    void insert(AppDO appDO);

    Result<List<AppDO>> selectAll();

    void startAppOnMachine(Long appId, String accessKey, String ipAddress, Integer port);
}
