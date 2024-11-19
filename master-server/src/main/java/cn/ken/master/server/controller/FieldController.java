package cn.ken.master.server.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.model.management.field.FieldPushReq;
import cn.ken.master.server.model.management.field.FieldVO;
import cn.ken.master.server.model.management.field.ManagementFieldDTO;
import cn.ken.master.server.model.management.field.ManagementFieldRequest;
import cn.ken.master.server.service.FieldService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/field")
public class FieldController {

    @Resource
    private FieldService fieldService;

    @GetMapping("/queryByNamespace")
    public Result<List<FieldDO>> queryByNamespaceId(@RequestParam("namespaceId") Long namespaceId) {
        return fieldService.selectByNamespaceId(namespaceId);
    }

    @GetMapping("/selectByCondition")
    public Result<List<ManagementFieldDTO>> selectByCondition(ManagementFieldRequest request) {
        return fieldService.selectByCondition(request);
    }

    @GetMapping("/list")
    public Result<List<FieldDO>> queryAllByAppId(Long appId) {
        return fieldService.selectByAppId(appId);
    }

    @PostMapping("/push")
    public Result<Boolean> pushFieldValue(@RequestBody FieldPushReq fieldPushReq) {
        return fieldService.pushFieldValue(fieldPushReq);
    }

    @GetMapping("/{fieldId}")
    public Result<FieldVO> getFieldValue(@PathVariable("fieldId") Long fieldId) {
        return fieldService.getFieldValue(fieldId);
    }

    @PostMapping(RequestPathConstant.SAVE)
    public Result<FieldDO> save(@RequestBody FieldDO field) {
        fieldService.insert(field);
        return Result.buildSuccess(field);
    }
}
