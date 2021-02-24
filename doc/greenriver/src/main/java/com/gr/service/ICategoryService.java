package com.gr.service;

import com.gr.common.ServerResponse;
import com.gr.pojo.Category;

import java.util.List;

/**
 * Created by 18334 on 2019/6/11.
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
