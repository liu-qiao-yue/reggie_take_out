package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itheima.reggie.enums.DeleteField.DELECTED;

/**
 * @author ellie
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private final SetmealDishService setmealDishService;


    public SetmealServiceImpl(SetmealDishService setmealDishService) {
        this.setmealDishService = setmealDishService;
    }



    /**
     * 根据分类id查询套餐数量
     * @param categoryId
     * @return
     */
    @Override
    public int getCountByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Setmeal> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.eq(Setmeal::getCategoryId, categoryId);
        return this.count(dishWrapper);
    }

    /**
     * 套餐page list
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> setmealPageList(Integer page, Integer pageSize, String name) {
        Page<SetmealDto> pageInfo = new Page<>(page, pageSize);

        MPJLambdaWrapper<Setmeal> wrapper = new MPJLambdaWrapper<Setmeal>()
                .selectAll(Setmeal.class)
                .selectAs(Category::getName, SetmealDto::getCategoryName)
                .leftJoin(Category.class, Category::getId, Setmeal::getCategoryId)
                .like(name != null, Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime);
        this.baseMapper.selectJoinPage(pageInfo, SetmealDto.class, wrapper);

        return pageInfo;
    }


    /**
     * 批量删除/批量启用/删除/启用
     *
     * @param status 状态
     * @param ids    套餐id，可用,分割
     * @return
     */
    @Override
    public boolean updateStatus(Integer status, String ids) {
        String[] idsArr = ids.split(",");
        List<Setmeal> setmeals = new ArrayList<>();
        Arrays.stream(idsArr).forEach(id -> {
            Setmeal.SetmealBuilder builder = Setmeal.builder().id(Long.parseLong(id));
            // 0 停售 1 起售 2 删除
            if (status != 2){
                builder.status(status);
            }else {
                builder.isDeleted(DELECTED.getValue());
            }
            setmeals.add(builder.build());
        });
        return this.updateBatchById(setmeals);

    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean saveWithDishes(SetmealDto setmealDto) {
        if (setmealDto.getId() == null) {
            // 新增套餐，状态默认为1
            setmealDto.setStatus(1);
        } else {
            setmealDishService.remove(Wrappers.lambdaQuery(SetmealDish.class).eq(SetmealDish::getSetmealId, setmealDto.getId()));
        }

        this.saveOrUpdate(setmealDto);
        // 保存套餐菜品表数据
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setPrice(null);
            setmealDish.setName(null);
            setmealDish.setSetmealId(setmealDto.getId());
        });
        setmealDishService.saveBatch(setmealDishes);
        return true;
    }

    /**
     * 根据id查询套餐和菜品
     * @param id
     * @return
     */
    @Override
    public SetmealDto getSetmealByIdWithDishes(String id) {
        MPJLambdaWrapper<Setmeal> wrapper = new MPJLambdaWrapper<Setmeal>()
                .selectAll(Setmeal.class)
                .selectCollection(SetmealDto::getSetmealDishes, map -> map
                        .id(SetmealDish::getId)
                        .result(SetmealDish::getDishId)
                        .result(SetmealDish::getSetmealId)
                        .result(SetmealDish::getCopies)
                        .result(Dish::getName, SetmealDish::getName)
                        .result(Dish::getPrice, SetmealDish::getPrice))
                .leftJoin(SetmealDish.class, SetmealDish::getSetmealId, Setmeal::getId)
                .innerJoin(Dish.class, Dish::getId, SetmealDish::getDishId)
                .eq(Setmeal::getId, id);
        return this.baseMapper.selectJoinOne(SetmealDto.class, wrapper);
    }
}
