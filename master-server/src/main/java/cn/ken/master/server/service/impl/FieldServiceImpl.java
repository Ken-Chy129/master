package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.entity.FieldDO;
import cn.ken.master.server.mapper.FieldMapper;
import cn.ken.master.server.service.FieldService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public Result<List<FieldDO>> selectAll() {
        QueryWrapper<FieldDO> queryWrapper = new QueryWrapper<>();
        List<FieldDO> fieldDOS = fieldMapper.selectList(queryWrapper);
        return Result.success(fieldDOS);
    }
}
