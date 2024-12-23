package cn.ken.master.server.app.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @GetMapping("current")
    public Result<Map<String, String>> current() {
        return Result.buildSuccess(Map.of("access", "", "name", "ken"));
    }
}
