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

    @DeleteMapping("deleteField")
    public Result<Boolean> deleteField(@RequestParam("templateFieldId") Long templateFieldId) {
        return templateFieldService.deleteById(templateFieldId);
    }

    @PostMapping("insert")
    public Result<Long> insert(@RequestBody TemplateFieldRequest request) {
        return templateService.insert(request);
    }

    @PostMapping("pushField")
    public Result<Boolean> pushField(@RequestBody TemplateFieldRequest request) {
        return templateFieldService.pushField(request);
    }

    @PostMapping("addField")
    public Result<Boolean> addField(@RequestBody TemplateFieldRequest request) {
        return templateFieldService.addField(request);
    }

    @PostMapping("push")
    public Result<Boolean> push(@RequestBody TemplateFieldRequest request) {
        return templateService.push(request);
    }
}
