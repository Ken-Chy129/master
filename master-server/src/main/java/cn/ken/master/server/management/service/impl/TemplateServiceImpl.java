package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.mapper.TemplateMapper;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;
import cn.ken.master.server.management.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Override
    public int insert(TemplateDO templateDO) {
        return templateMapper.insert(templateDO);
    }

    @Override
    public List<TemplateDO> selectAll() {
        return List.of();
    }

    @Override
    public Result<List<TemplateDO>> selectByAppId(Long appId) {
        return Result.buildSuccess(templateMapper.selectTemplateListByAppId(appId));
    }
}
