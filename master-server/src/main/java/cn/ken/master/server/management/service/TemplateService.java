package cn.ken.master.server.management.service;


import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;

import java.util.List;

public interface TemplateService {

    int insert(TemplateDO templateDO);

    List<TemplateDO> selectAll();

    Result<List<TemplateDO>> selectByAppId(Long appId);
}
