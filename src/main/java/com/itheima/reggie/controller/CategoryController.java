package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ellie
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 新增菜品/套餐分类
     * @param category
     * @return
     */
    @PostMapping
    public R<Boolean> addCategory(@RequestBody Category category) {
        return R.success(categoryService.addCategory(category));
    }

    /**
     * 分类分页查询
     * @param page 第几行
     * @param pageSize 每页几行
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> categoryPageList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        return R.success(categoryService.categoryPageList(page,pageSize));
    }

    /**
     * 修改菜品/套餐分类
     * @param category
     * @return
     */
    @PutMapping
    public R<Boolean> updateCategory(@RequestBody Category category) {
        return R.success(categoryService.updateById(category));
    }

    /**
     * 删除菜品/套餐分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<Boolean> deleteCategory(Long ids) {
        return R.success(categoryService.deleteCategory(ids));
    }

    /**
     * 获取菜品/套餐分类列表by type
     * @param category 1:菜品分类 2:套餐分类
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getCategoryList(Category category) {
        return R.success(categoryService.getCategoryList(category));
    }
}
