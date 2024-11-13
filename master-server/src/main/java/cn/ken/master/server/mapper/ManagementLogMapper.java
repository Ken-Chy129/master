package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.ManagementLogDO;
import cn.ken.master.server.model.management.log.ManagementLogDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ManagementLogMapper extends BaseMapper<ManagementLogDO> {

    List<ManagementLogDO> selectByFieldId(Long fieldId);

    List<ManagementLogDO> selectByCondition(@Param("managementLog") ManagementLogDTO managementLogDTO, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}
