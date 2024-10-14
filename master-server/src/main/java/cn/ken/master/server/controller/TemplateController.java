package cn.ken.master.server.controller;

import cn.ken.master.server.common.RequestPathConstant;
import cn.ken.master.server.model.entity.TemplateDO;
import cn.ken.master.server.service.TemplateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("template")
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @PostMapping(RequestPathConstant.SAVE)
    public int save(@RequestBody TemplateDO templateDO) {
        return templateService.insert(templateDO);
    }
}
