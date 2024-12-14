package cn.ken.master.server.management.mapper;

import cn.ken.master.server.management.model.entity.NamespaceDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NamespaceMapper extends BaseMapper<NamespaceDO> {

    int insert(NamespaceDO namespaceDO);
}
