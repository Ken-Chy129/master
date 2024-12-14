package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateFieldMapper extends BaseMapper<TemplateFieldDO> {

    int insert(TemplateFieldDO templateFieldDO);

    void updateFieldValueByName(@Param("appId") Long appId, @Param("name") String name, @Param("fieldValue") String fieldValue);

    void updateFieldValueById(@Param("id") Long id, @Param("fieldValue") String fieldValue);

    List<TemplateFieldDO> selectByTemplateName(@Param("appId") Long appId, @Param("name") String name);

    List<TemplateFieldDO> selectByTemplateId(@Param("templateId") Long templateId);

    Long count(TemplateFieldQuery query);

    List<TemplateFieldDO> selectByCondition(TemplateFieldQuery query);

    void deleteByFieldId(Long fieldId);
}
