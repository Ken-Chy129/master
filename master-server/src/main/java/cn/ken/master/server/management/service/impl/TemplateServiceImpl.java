package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.core.AuthContext;
import cn.ken.master.server.management.mapper.TemplateFieldMapper;
import cn.ken.master.server.management.model.entity.TemplateDO;
import cn.ken.master.server.management.mapper.TemplateMapper;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldQuery;
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

    @Resource
    private TemplateFieldMapper templateFieldMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Long> insert(TemplateFieldRequest request) {
        TemplateDO templateDO = new TemplateDO();
        templateDO.setAppId(AuthContext.getAppId());
        templateDO.setName(request.getName());
        templateDO.setDescription(request.getDescription());
        templateMapper.insert(templateDO);

        // 复制源
        Long fromTemplateId = request.getFromTemplateId();
        if (fromTemplateId == null) {
            return Result.buildSuccess(templateDO.getId());
        }

        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateId(fromTemplateId);
        for (TemplateFieldDO templateFieldDO : templateFieldDOList) {
            templateFieldDO.setId(null);
            templateFieldDO.setTemplateId(templateDO.getId());
        }
        templateFieldMapper.insert(templateFieldDOList);
        return Result.buildSuccess(templateDO.getId());
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
