package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.FieldDO;
import cn.ken.master.server.management.model.management.field.ManagementFieldDTO;
import cn.ken.master.server.management.model.management.field.ManagementFieldQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FieldMapper extends BaseMapper<FieldDO> {

    List<ManagementFieldDTO> selectByNamespaceId(String namespaceId);

    Long count(ManagementFieldQuery query);

    List<ManagementFieldDTO> selectByCondition(ManagementFieldQuery query);
}
