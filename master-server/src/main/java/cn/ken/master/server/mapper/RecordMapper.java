package cn.ken.master.server.mapper;

import cn.ken.master.server.model.entity.RecordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<RecordDO> {

    List<RecordDO> selectByFieldId(Long fieldId);

}
