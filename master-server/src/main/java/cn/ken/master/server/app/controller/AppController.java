package cn.ken.master.server.app.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.app.model.category.CategoryVO;
import cn.ken.master.server.management.model.entity.AppDO;
import cn.ken.master.server.management.model.entity.MachineDO;
import cn.ken.master.server.app.service.AppService;
import cn.ken.master.server.app.service.CategoryService;
import cn.ken.master.server.management.service.MachineService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用控制器
 * @author Ken-Chy129
 * @date 2024/8/28
 */
@RestController
@RequestMapping("master")
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private MachineService machineService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("apps")
    public Result<List<AppDO>> queryAllApps() {
        return appService.selectAll();
    }

    @PostMapping(RequestPathConstant.SAVE)
    public void saveApp(@RequestBody AppDO appDO) {
        appService.insert(appDO);
    }

    @GetMapping("machines")
    public Result<List<MachineDO>> queryMachines(@RequestParam("appId") Long appId) {
        return machineService.selectAll(appId);
    }

    @PostMapping("addMachine")
    public void addMachine(@RequestBody MachineDO machineDO) {
        machineService.insert(machineDO);
    }

    @GetMapping("categories")
    public Result<List<CategoryVO>> queryCategories() {
        return categoryService.selectAllCategory();
    }
}
