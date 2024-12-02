package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.TemplateFieldDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TemplateFieldMapper extends BaseMapper<TemplateFieldDO> {

    /**
     * 更新应用默认模板的字段值
     * @param appId 应用id
     * @param fieldValue 字段值
     */
    void updateAppDefaultTemplateField(Long appId, String fieldValue);


}
