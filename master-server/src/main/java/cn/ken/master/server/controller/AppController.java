package cn.ken.master.server.controller;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.model.category.CategoryVO;
import cn.ken.master.server.model.entity.AppDO;
import cn.ken.master.server.model.entity.MachineDO;
import cn.ken.master.server.service.AppService;
import cn.ken.master.server.service.CategoryService;
import cn.ken.master.server.service.MachineService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用控制器
 * @author Ken-Chy129
 * @date 2024/8/28
 */
@RestController
@RequestMapping("app")
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private MachineService machineService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("list")
    public Result<List<AppDO>> list() {
        return appService.selectAll();
    }

    @PostMapping(RequestPathConstant.SAVE)
    public void saveApp(@RequestBody AppDO appDO) {
        appService.insert(appDO);
    }

    @GetMapping("queryMachines")
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
