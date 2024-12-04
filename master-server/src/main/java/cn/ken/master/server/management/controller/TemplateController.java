package cn.ken.master.server.management.controller;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.service.TemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @PostMapping(RequestPathConstant.SAVE)
    public int save(@RequestBody TemplateDO templateDO) {
        return templateService.insert(templateDO);
    }

    @GetMapping("selectByAppId")
    public Result<List<TemplateDO>> selectByAppId(@RequestParam("appId") Long appId) {
        return templateService.selectByAppId(appId);
    }

}
