package cn.ken.master.server.app.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.constant.RequestPathConstant;
import cn.ken.master.server.app.model.category.CategoryVO;
import cn.ken.master.server.app.model.entity.AppDO;
import cn.ken.master.server.app.service.AppService;
import cn.ken.master.server.app.service.CategoryService;
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
    private CategoryService categoryService;

    @GetMapping("list")
    public Result<List<AppDO>> list() {
        return appService.selectAll();
    }

    @PostMapping(RequestPathConstant.SAVE)
    public void save(@RequestBody AppDO appDO) {
        appService.insert(appDO);
    }

    @GetMapping("categories")
    public Result<List<CategoryVO>> queryCategories() {
        return categoryService.selectAllCategory();
    }
}
