package cn.ken.master.server.management.service;


import cn.ken.master.server.management.model.entity.TemplateDO;

import java.util.List;

public interface TemplateService {

    int insert(TemplateDO templateDO);

    List<TemplateDO> selectAll();
}
