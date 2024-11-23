package cn.ken.master.server.app.service;

import cn.ken.master.core.model.common.Result;
import cn.ken.master.server.app.model.category.CategoryVO;

import java.util.List;

public interface CategoryService {

    Result<List<CategoryVO>> selectAllCategory();
}
