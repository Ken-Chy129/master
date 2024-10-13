package cn.ken.master.server.controller;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.NamespaceDO;
import cn.ken.master.server.service.NamespaceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("namespace")
public class NamespaceController {

    @Resource
    private NamespaceService namespaceService;

    @GetMapping
    private Result<List<NamespaceDO>> list() {
        return namespaceService.selectAll();
    }

    @PostMapping
    private Result<NamespaceDO> create(@RequestBody NamespaceDO namespaceDO) {
        namespaceService.insert(namespaceDO);
        return Result.success(namespaceDO);
    }
}
