package cn.ken.master.server.management.service;

import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;

import java.util.List;

public interface TemplateFieldService {

    int insert(TemplateFieldDO templateFieldDO);

    List<TemplateFieldDO> selectAll();

    List<ManageableFieldDTO> getTemplateFields(Long appId, String defaultTemplateName);
}
