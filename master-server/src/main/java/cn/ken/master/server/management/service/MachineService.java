package cn.ken.master.server.management.service;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.MachineDO;

import java.util.List;

public interface MachineService {

    Result<List<MachineDO>> selectAll(Long appId);

    void insert(MachineDO machineDO);
}
