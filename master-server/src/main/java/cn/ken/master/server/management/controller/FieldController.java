package cn.ken.master.server.management.controller;

import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.management.field.FieldPushReq;
import cn.ken.master.server.management.model.management.field.FieldVO;
import cn.ken.master.server.management.model.management.field.ManagementFieldDTO;
import cn.ken.master.server.management.model.management.field.ManagementFieldRequest;
import cn.ken.master.server.management.service.FieldService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/field")
public class FieldController {

    @Resource
    private FieldService fieldService;

    @GetMapping("/selectByNamespaceId")
    public Result<List<ManagementFieldDTO>> selectByNamespaceId(@RequestParam("namespaceId") String namespaceId) {
        return fieldService.selectByNamespaceId(namespaceId);
    }

    @GetMapping("/selectByCondition")
    public PageResult<List<ManagementFieldDTO>> selectByCondition(ManagementFieldRequest request) {
        return fieldService.selectByCondition(request);
    }

    @PostMapping("/push")
    public Result<Boolean> pushFieldValue(@RequestBody FieldPushReq fieldPushReq) {
        return fieldService.pushFieldValue(fieldPushReq);
    }

    @GetMapping("/{fieldId}")
    public Result<FieldVO> getFieldValue(@PathVariable("fieldId") Long fieldId) {
        return fieldService.getFieldValue(fieldId);
    }

}
