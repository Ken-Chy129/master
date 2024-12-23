package cn.ken.master.server.management.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.constant.RequestPathConstant;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.service.NamespaceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/namespace")
public class NamespaceController {

    @Resource
    private NamespaceService namespaceService;

    @GetMapping("/selectByAppId")
    public Result<List<NamespaceDO>> selectByAppId(@RequestParam("appId") Long appId) {
        return namespaceService.selectByAppId(appId);
    }

    @PostMapping(RequestPathConstant.SAVE)
    public Result<NamespaceDO> save(@RequestBody NamespaceDO namespaceDO) {
        namespaceService.insert(namespaceDO);
        return Result.buildSuccess(namespaceDO);
    }
}
