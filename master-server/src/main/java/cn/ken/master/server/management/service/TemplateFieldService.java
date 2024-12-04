package cn.ken.master.server.management.service;

import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;

import java.util.List;

public interface TemplateFieldService {

    int insert(TemplateFieldDO templateFieldDO);

    List<TemplateFieldDO> selectAll();

    List<ManageableFieldDTO> getTemplateFields(Long appId, String defaultTemplateName);

    PageResult<List<TemplateFieldDO>> selectFieldByCondition(TemplateFieldRequest request);
}
