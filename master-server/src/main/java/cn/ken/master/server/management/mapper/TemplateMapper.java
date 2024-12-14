package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemplateMapper extends BaseMapper<TemplateDO> {

    int insert(TemplateDO templateDO);

    Long selectTemplateId(Long appId, String name);

    List<TemplateDO> selectTemplateListByAppId(Long appId);
}
