package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.FieldDO;
import cn.ken.master.server.mapper.FieldMapper;
import cn.ken.master.server.service.FieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    @Resource
    private FieldMapper fieldMapper;

    @Override
    public void insert(FieldDO fieldDO) {
        fieldMapper.insert(fieldDO);
    }

    @Override
    public Result<List<FieldDO>> selectByNamespaceId(Long namespaceId) {
        LambdaQueryWrapper<FieldDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FieldDO::getNamespaceId, namespaceId);
        List<FieldDO> fieldDOS = fieldMapper.selectList(queryWrapper);
        return Result.success(fieldDOS);
    }

    @Override
    public Result<List<FieldDO>> selectByAppId(Long appId) {
        LambdaQueryWrapper<FieldDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FieldDO::getAppId, appId);
        List<FieldDO> fieldDOS = fieldMapper.selectList(queryWrapper);
        return Result.success(fieldDOS);
    }
}
