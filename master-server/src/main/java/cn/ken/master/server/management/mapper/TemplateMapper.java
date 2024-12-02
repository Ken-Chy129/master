package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateMapper extends BaseMapper<TemplateDO> {

    Long selectAppDefaultTemplateId(Long appId);

}
