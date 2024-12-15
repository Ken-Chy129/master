package cn.ken.master.server.management.controller;

import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;
import cn.ken.master.server.management.service.TemplateFieldService;
import cn.ken.master.server.management.service.TemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @Resource
    private TemplateFieldService templateFieldService;

    @GetMapping("selectByAppId")
    public Result<List<TemplateDO>> selectByAppId(@RequestParam("appId") Long appId) {
        return templateService.selectByAppId(appId);
    }

    @DeleteMapping("deleteById")
    public Result<Boolean> deleteById(@RequestParam("templateId") Long templateId) {
        return templateService.deleteById(templateId);
    }

    @GetMapping("selectFieldPageByCondition")
    public PageResult<List<TemplateFieldDO>> selectFieldPageByCondition(TemplateFieldRequest request) {
        return templateFieldService.selectFieldByCondition(request);
    }

    @PostMapping("updateField")
    public Result<Boolean> updateField(@RequestBody TemplateFieldRequest request) {
        return templateFieldService.updateField(request);
    }

    @PostMapping("insert")
    public Result<Long> insert(@RequestBody TemplateFieldRequest request) {
        return templateService.insert(request);
    }

    @PostMapping("pushTemplateField")
    public Result<Boolean> pushTemplateField(@RequestBody TemplateFieldRequest request) {
        return templateFieldService.updateField(request);
    }

    @PostMapping("addField")
    public Result<Boolean> addField(@RequestBody TemplateFieldRequest request) {
        return templateFieldService.addField(request);
    }
}
