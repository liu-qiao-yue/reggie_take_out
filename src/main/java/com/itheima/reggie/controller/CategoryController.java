package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R<Object> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 分类分页查询
     * @param page 第几行
     * @param pageSize 每页几行
     * @return
     */
    @GetMapping("/page")
    public R<Object> categoryPageList(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        return categoryService.categoryPageList(page,pageSize);
    }

    /**
     * 修改菜品/套餐分类
     * @param category
     * @return
     */
    @PutMapping
    public R<Object> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    /**
     * 删除菜品/套餐分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<Object> deleteCategory(Long ids) {
        return categoryService.deleteCategory(ids);
    }

    /**
     * 获取菜品/套餐分类列表by type
     * @param type 1:菜品分类 2:套餐分类
     * @return
     */
    @GetMapping("/list")
    public R<Object> getCategoryList(@RequestParam("type") String type) {
        return categoryService.getCategoryList(type);
    }
}
