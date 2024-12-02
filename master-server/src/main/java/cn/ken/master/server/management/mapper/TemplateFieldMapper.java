package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateFieldMapper extends BaseMapper<TemplateFieldDO> {

    void updateFieldValue(Long appId, String name, String fieldValue);

}
