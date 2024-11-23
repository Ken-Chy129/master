package cn.ken.master.server.management.service;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;

import java.util.List;

public interface TemplateFieldService {

    int insert(TemplateFieldDO templateFieldDO);

    List<TemplateFieldDO> selectAll();
}
