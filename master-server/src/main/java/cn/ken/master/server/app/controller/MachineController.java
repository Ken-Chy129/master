package cn.ken.master.server.app.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.app.model.entity.MachineDO;
import cn.ken.master.server.app.service.MachineService;
import cn.ken.master.server.common.base.BaseController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("machine")
public class MachineController extends BaseController {

    @Resource
    private MachineService machineService;

    @GetMapping("list")
    public Result<List<MachineDO>> list() {
        Long appId = getAppId();
        return machineService.selectAll(appId);
    }

    @PostMapping("save")
    public void save(@RequestBody MachineDO machineDO) {
        machineService.insert(machineDO);
    }
}
