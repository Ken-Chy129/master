package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.MachineDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MachineMapper extends BaseMapper<MachineDO> {

    List<MachineDO> selectByAppId(@Param("appId") Long appId);
}
