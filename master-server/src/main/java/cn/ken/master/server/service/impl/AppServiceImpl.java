package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.common.AppStartException;
import cn.ken.master.server.common.enums.MachineStatus;
import cn.ken.master.server.mapper.FieldMapper;
import cn.ken.master.server.mapper.MachineMapper;
import cn.ken.master.server.mapper.NamespaceMapper;
import cn.ken.master.server.model.entity.AppDO;
import cn.ken.master.server.mapper.AppMapper;
import cn.ken.master.server.model.entity.MachineDO;
import cn.ken.master.server.service.AppService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;

    @Resource
    private MachineMapper machineMapper;

    @Override
    public void insert(AppDO appDO) {
        appMapper.insert(appDO);
    }

    @Override
    public Result<List<AppDO>> selectAll() {
        List<AppDO> appDOList = appMapper.selectList(new QueryWrapper<>());
        return Result.success(appDOList);
    }

    @Override
    public void startAppOnMachine(Long appId, String accessKey, String ipAddress, Integer port) {
        // todo：分为通用的绑定机器和Management
        // 1.查询应用
        AppDO appDOList = appMapper.selectById(appId);
        if (appDOList == null) {
            throw new AppStartException("应用不存在");
        }
        // todo：加密
        if (!appDOList.getAccessKey().equals(accessKey)) {
            throw new AppStartException("应用密钥不正确");
        }
        // 2.绑定机器
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
        } else {
            Long id = machineDO.getId();
            machineDO = new MachineDO();
            machineDO.setId(id);
        }
        machineDO.setStatus(MachineStatus.RUNNING.getCode());
        machineMapper.insertOrUpdate(machineDO);
    }
}
