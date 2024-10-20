package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.common.enums.MachineStatus;
import cn.ken.master.server.model.entity.MachineDO;
import cn.ken.master.server.mapper.MachineMapper;
import cn.ken.master.server.service.MachineService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {

    @Resource
    private MachineMapper machineMapper;

    @Override
    public Result<List<MachineDO>> selectAll(Long appId) {
        LambdaQueryWrapper<MachineDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MachineDO::getAppId, appId);
        List<MachineDO> machineDOS = machineMapper.selectList(queryWrapper);
        return Result.success(machineDOS);
    }

    @Override
    public void insert(MachineDO machineDO) {
        machineMapper.insert(machineDO);
    }

    @Override
    public void bindMachine(Long appId, String ipAddress, Integer port) {
        LambdaQueryWrapper<MachineDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MachineDO::getAppId, appId)
                    .eq(MachineDO::getIpAddress, ipAddress)
                    .eq(MachineDO::getPort, port);
        MachineDO machineDO = machineMapper.selectOne(queryWrapper);
        if (machineDO == null) {
            machineDO = new MachineDO();
            machineDO.setAppId(appId);
            machineDO.setIpAddress(ipAddress);
            machineDO.setPort(port);
        }
        machineDO.setStatus(MachineStatus.RUNNING.getCode());
        machineMapper.insertOrUpdate(machineDO);
    }
}
