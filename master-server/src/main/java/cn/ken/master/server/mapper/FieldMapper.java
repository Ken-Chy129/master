package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.FieldDO;
import cn.ken.master.server.model.management.field.ManagementFieldDTO;
import cn.ken.master.server.model.management.field.ManagementFieldQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FieldMapper extends BaseMapper<FieldDO> {

    Long count(ManagementFieldQuery query);

    List<ManagementFieldDTO> selectByCondition(ManagementFieldQuery query);
}
