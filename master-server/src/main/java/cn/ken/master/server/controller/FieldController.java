package cn.ken.master.server.controller;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.FieldDO;
import cn.ken.master.server.service.FieldService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("field")
public class FieldController {

    @Resource
    private FieldService fieldService;

    @GetMapping
    private Result<List<FieldDO>> list() {
        return fieldService.selectAll();
    }

    @PostMapping
    private Result<FieldDO> save(@RequestBody FieldDO field) {
        fieldService.insert(field);
        return Result.success(field);
    }
}
