package com.yandong.service;

import com.yandong.common.ServerResponse;
import com.yandong.pojo.Category;

import java.util.List;

/**
 * Created by yandong on 2017/5/19.
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName,Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
