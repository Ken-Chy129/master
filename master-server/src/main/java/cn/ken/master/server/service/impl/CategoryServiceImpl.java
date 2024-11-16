package cn.ken.master.server.service.impl;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.mapper.CategoryMapper;
import cn.ken.master.server.model.category.Category;
import cn.ken.master.server.model.category.CategoryVO;
import cn.ken.master.server.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public Result<List<CategoryVO>> selectAllCategory() {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<>());
        List<CategoryVO> result = new ArrayList<>();
        categoryList.stream()
                .filter(Category::isRoot)
                .map(Category::toCategoryVO)
                .forEach(result::add);
        result.forEach(
                parentCategory -> parentCategory.setChildren(
                        categoryList.stream()
                            .filter(category -> parentCategory.getId().equals(category.getParentId()))
                            .map(Category::toCategoryVO)
                            .collect(Collectors.toList())
                )
        );
        return Result.buildSuccess(result);
    }
}
