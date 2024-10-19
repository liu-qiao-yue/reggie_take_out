package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    R<Object> addCategory(Category category);

    R<Object> categoryPageList(Integer page, Integer pageSize);

    R<Object> updateCategory(Category category);

    R<Object> deleteCategory(Long id);

    R<Object> getCategoryList(String type);
}
