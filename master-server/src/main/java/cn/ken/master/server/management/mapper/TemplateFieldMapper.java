package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemplateFieldMapper extends BaseMapper<TemplateFieldDO> {

    void updateFieldValue(Long appId, String name, String fieldValue);

    List<TemplateFieldDO> selectByTemplateId(Long appId, String name);

    void deleteByFieldId(Long fieldId);
}
