package cn.ken.master.server.app.service.impl;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.app.model.entity.MachineDO;
import cn.ken.master.server.app.mapper.MachineMapper;
import cn.ken.master.server.app.service.MachineService;
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
        return Result.buildSuccess(machineDOS);
    }

    @Override
    public void insert(MachineDO machineDO) {
        machineMapper.insert(machineDO);
    }

}
