package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.ManagementLogDO;
import cn.ken.master.server.management.model.management.log.ManagementLogQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagementLogMapper extends BaseMapper<ManagementLogDO> {

    int insert(ManagementLogDO record);

    List<ManagementLogDO> selectByFieldId(Long fieldId);

    Long count(ManagementLogQuery query);

    List<ManagementLogDO> selectByCondition(ManagementLogQuery managementLogQuery);
}
