package cn.ken.master.server.app.controller;

import cn.ken.master.core.model.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class CommonController {

    @GetMapping("currentUser")
    public Result<Map<String, String>> currentUser() {
        return Result.buildSuccess(Map.of("access", "", "name", "ken"));
    }
}
