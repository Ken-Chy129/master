package cn.ken.master.server.management.service.impl;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.management.model.entity.NamespaceDO;
import cn.ken.master.server.management.mapper.NamespaceMapper;
import cn.ken.master.server.management.service.NamespaceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamespaceServiceImpl implements NamespaceService {

    @Resource
    private NamespaceMapper namespaceMapper;

    @Override
    public void insert(NamespaceDO namespaceDO) {
        namespaceMapper.insert(namespaceDO);
    }

    @Override
    public Result<List<NamespaceDO>> selectByAppId(Long appId) {
        LambdaQueryWrapper<NamespaceDO> nameSpaceQueryWrapper = new LambdaQueryWrapper<>();
        nameSpaceQueryWrapper.eq(NamespaceDO::getAppId, appId);
        List<NamespaceDO> namespaceDOS = namespaceMapper.selectList(nameSpaceQueryWrapper);
        return Result.buildSuccess(namespaceDOS);
    }
}
