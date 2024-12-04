package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.model.ManageableFieldDTO;
import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.mapper.TemplateFieldMapper;
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
    private TemplateFieldMapper templateFieldMapper;

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
        List<TemplateFieldDO> templateFieldDOList = templateFieldMapper.selectByTemplateId(appId, templateName);
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
}
