package cn.ken.master.server.service;

import cn.ken.master.core.model.Result;
import cn.ken.master.server.model.category.CategoryVO;

import java.util.List;

public interface CategoryService {

    Result<List<CategoryVO>> selectAllCategory();
}
