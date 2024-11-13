package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.ManagementLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagementLogMapper extends BaseMapper<ManagementLogDO> {

    List<ManagementLogDO> selectByFieldId(Long fieldId);

}
