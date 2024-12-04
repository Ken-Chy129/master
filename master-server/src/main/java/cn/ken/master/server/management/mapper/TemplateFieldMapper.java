package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import cn.ken.master.server.management.model.management.template.TemplateFieldQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateFieldMapper extends BaseMapper<TemplateFieldDO> {

    void updateFieldValue(Long appId, String name, String fieldValue);

    List<TemplateFieldDO> selectByTemplateName(@Param("appId") Long appId, @Param("name") String name);

    Long count(TemplateFieldQuery query);

    List<TemplateFieldDO> selectByCondition(TemplateFieldQuery query);

    void deleteByFieldId(Long fieldId);

}
