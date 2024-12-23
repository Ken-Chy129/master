package cn.ken.master.server.app.mapper;

import cn.ken.master.server.app.model.entity.MachineDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MachineMapper extends BaseMapper<MachineDO> {

    int insert(MachineDO record);

    List<MachineDO> selectByAppId(@Param("appId") Long appId);
}
