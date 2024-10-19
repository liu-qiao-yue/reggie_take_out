package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.enums.BizExceptionEnum;
import com.itheima.reggie.enums.DeleteField;
import com.itheima.reggie.exception.BizException;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private final DishService dishService;

    @Autowired
    private final SetmealService setmealService;

    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    /**
     * 新增菜品或套餐分类
     * @param category
     * @return
     */
    @Override
    public R<Object> addCategory(Category category) {
        //验证name唯一
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Category::getName, category.getName());
        if(this.count(wrapper) > 0)
            throw new BizException(BizExceptionEnum.CATEGORY_IS_EXIST);

        //保存数据
        return R.success(this.save(category));
    }

    /**
     * 菜品分类分页查询
     * @param page 第几页
     * @param pageSize 每页几行
     * @return
     */
    @Override
    public R<Object> categoryPageList(Integer page, Integer pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Category::getSort);
        wrapper.eq(Category::getIsDeleted, DeleteField.ACTITVE.getValue());
        return R.success(this.baseMapper.selectPage(categoryPage, wrapper));
    }

    @Override
    public R<Object> updateCategory(Category category) {
        return R.success(this.updateById(category));
    }

    @Override
    public R<Object> deleteCategory(Long id) {
        //查询你当前分类是否关联了菜品或套餐，如果已经关联，抛出异常
        if (!canDeleteCategory(id))
            throw new BizException(BizExceptionEnum.CATEGORY_IS_RELATED);

        //正常删除分类
        Category category = Category.builder()
                .id(id)
                .isDeleted(DeleteField.DELECTED.getValue())
                .build();
        return R.success(this.updateById(category));
    }

    @Override
    public R<Object> getCategoryList(String type) {
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Category::getType, type);
        wrapper.eq(Category::getIsDeleted, DeleteField.ACTITVE.getValue());
        return R.success(this.list(wrapper));
    }


    /**
     * 判断是否允许删除当前分类
     * 无法删除：菜品或套餐关联分类ID
     * @param id 分类ID
     * @return true(菜品/套餐 分类ID 为0) false(菜品/套餐 分类ID 不为0)
     */
    private boolean canDeleteCategory(Long id) {
        return dishService.getCountByCategoryId(id) == 0
                && setmealService.getCountByCategoryId(id) == 0;
    }
}
