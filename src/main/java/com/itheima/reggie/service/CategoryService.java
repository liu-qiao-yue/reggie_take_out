package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    boolean addCategory(Category category);

    Page<Category> categoryPageList(Integer page, Integer pageSize);

    boolean deleteCategory(Long id);

    List<Category> getCategoryList(Category category);
}
