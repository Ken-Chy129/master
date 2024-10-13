package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.MachineDO;

import java.util.List;

public interface MachineService {

    Result<List<MachineDO>> selectAll(Long appId);

    void insert(MachineDO machineDO);
}
