package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

import java.util.List;

/**
 * @author ellie
 */
public interface CategoryService extends IService<Category> {
    /**
     * 新增菜品/套餐分类
     * @param category
     * @return
     */
    boolean addCategory(Category category);

    /**
     * 菜品/套餐 分页
     * @param page
     * @param pageSize
     * @return
     */

    Page<Category> categoryPageList(Integer page, Integer pageSize);

    /**
     * 删除菜品/套餐
     * @param id
     * @return
     */

    boolean deleteCategory(Long id);

    /**
     * 获取菜品/套餐集合
     * @param category
     * @return
     */

    List<Category> getCategoryList(Category category);
}
