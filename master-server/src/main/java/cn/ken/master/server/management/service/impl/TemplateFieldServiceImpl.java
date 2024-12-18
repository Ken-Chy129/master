package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.core.model.common.PageResult;
import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.mapper.FieldMapper;
import cn.ken.master.server.management.mapper.NamespaceMapper;
import cn.ken.master.server.management.mapper.TemplateMapper;
import cn.ken.master.server.management.model.entity.FieldDO;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.mapper.TemplateFieldMapper;
import cn.ken.master.server.management.model.management.template.TemplateFieldQuery;
import cn.ken.master.server.management.model.management.template.TemplateFieldRequest;
import cn.ken.master.server.management.service.TemplateFieldService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TemplateFieldServiceImpl implements TemplateFieldService {

    @Resource
    private TemplateMapper templateMapper;
    @Resource
    private TemplateFieldMapper templateFieldMapper;
    @Resource
    private NamespaceMapper namespaceMapper;
    @Resource
    private FieldMapper fieldMapper;

    @Override
    public int insert(TemplateFieldDO templateFieldDO) {
        System.out.println(templateFieldDO);
        return templateFieldMapper.insert(templateFieldDO);
    }

    @Override
    public List<TemplateFieldDO> selectAll() {
        return List.of();
    }

    @Override
    public List<ManageableFieldDTO> getTemplateFields(Long appId, String templateName) {
        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateName(appId, templateName);
        if (CollectionUtils.isEmpty(templateFieldDOList)) {
            return Collections.emptyList();
        }
        return templateFieldDOList.stream()
                .map(templateFieldDO -> {
                    ManageableFieldDTO manageableFieldDTO = new ManageableFieldDTO();
                    manageableFieldDTO.setNamespace(templateFieldDO.getNamespace());
                    manageableFieldDTO.setName(templateFieldDO.getFieldName());
                    manageableFieldDTO.setValue(templateFieldDO.getFieldValue());
                    return manageableFieldDTO;
                })
                .toList();
    }

    @Override
    public PageResult<List<TemplateFieldDO>> selectFieldByCondition(TemplateFieldRequest request) {
        System.out.println(request);
        TemplateFieldQuery templateFieldQuery = TemplateFieldQuery.of(request);
        Long namespaceId = request.getNamespaceId();
        if (namespaceId != null && request.getNamespace() == null) {
            NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
            templateFieldQuery.setNamespace(namespaceDO.getName());
        }
        Long count = templateFieldMapper.count(templateFieldQuery);
        if (count == 0) {
            return PageResult.buildSuccess();
        }
        List<TemplateFieldDO> templateFieldDOS = templateFieldMapper.selectByCondition(templateFieldQuery);
        PageResult<List<TemplateFieldDO>> result = PageResult.buildSuccess(templateFieldDOS);
        result.setPageCount(count);
        return result;
    }

    @Override
    public Result<Boolean> updateField(TemplateFieldRequest request) {
        templateFieldMapper.updateFieldValueById(request.getId(), request.getFieldValue());
        return Result.buildSuccess(true);
    }

    @Override
    public Result<Boolean> addField(TemplateFieldRequest request) {
        TemplateFieldQuery templateFieldQuery = new TemplateFieldQuery();
        templateFieldQuery.setFieldId(request.getFieldId());
        templateFieldQuery.setTemplateId(request.getTemplateId());
        Long count = templateFieldMapper.count(templateFieldQuery);
        if (count > 0) {
            return Result.buildError("模板中已存在该字段值");
        }

        FieldDO fieldDO = fieldMapper.selectById(request.getFieldId());
        if (fieldDO == null) {
            return Result.buildError("查询不到对应的字段");
        }

        Long namespaceId = fieldDO.getNamespaceId();
        NamespaceDO namespaceDO = namespaceMapper.selectById(namespaceId);
        if (namespaceDO == null) {
            return Result.buildError("查询不到对应的命名空间");
        }

        TemplateFieldDO templateFieldDO = new TemplateFieldDO();
        templateFieldDO.setTemplateId(request.getTemplateId());
        templateFieldDO.setFieldId(request.getFieldId());
        templateFieldDO.setNamespace(namespaceDO.getName());
        templateFieldDO.setFieldName(fieldDO.getName());
        templateFieldDO.setFieldValue(request.getFieldValue());
        templateFieldMapper.insert(templateFieldDO);
        return Result.buildSuccess(Boolean.TRUE);
    }

}
