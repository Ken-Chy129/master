package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.model.management.log.ManagementLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManagementLogMapper extends BaseMapper<ManagementLogDO> {

    List<ManagementLogDO> selectByFieldId(Long fieldId);

    Long count(ManagementLogQuery query);

    List<ManagementLogDO> selectByCondition(ManagementLogQuery managementLogQuery);
}
