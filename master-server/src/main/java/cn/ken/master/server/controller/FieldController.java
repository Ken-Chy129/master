package cn.ken.master.server.controller;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.service.FieldService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("field")
public class FieldController {

    @Resource
    private FieldService fieldService;

    @GetMapping("/{namespaceId}")
    private Result<List<FieldDO>> queryByNamespaceId(@PathVariable("namespaceId") Long namespaceId) {
        return fieldService.selectByNamespaceId(namespaceId);
    }

    @GetMapping("/list")
    private Result<List<FieldDO>> queryAllByAppId(Long appId) {
        return fieldService.selectByAppId(appId);
    }

    @PostMapping(RequestPathConstant.SAVE)
    private Result<FieldDO> save(@RequestBody FieldDO field) {
        fieldService.insert(field);
        return Result.success(field);
    }
}
