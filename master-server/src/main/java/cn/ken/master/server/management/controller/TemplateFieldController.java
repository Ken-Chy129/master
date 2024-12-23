package cn.ken.master.server.management.controller;

import cn.ken.master.server.common.constant.RequestPathConstant;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.service.TemplateFieldService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("templateField")
public class TemplateFieldController {

    @Resource
    private TemplateFieldService templateFieldService;

    @PostMapping(RequestPathConstant.SAVE)
    public int save(@RequestBody TemplateFieldDO templateFieldDO) {
        return templateFieldService.insert(templateFieldDO);
    }
}
