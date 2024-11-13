package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itheima.reggie.enums.DeleteField.ACTITVE;
import static com.itheima.reggie.enums.DeleteField.DELECTED;

/**
 * @author ellie
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private final DishFlavorService dishFlavorService;

    @Autowired
    private final DishMapper dishMapper;

    public DishServiceImpl(DishFlavorService dishFlavorService, DishMapper dishMapper) {
        this.dishFlavorService = dishFlavorService;
        this.dishMapper = dishMapper;
    }

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Override
    public int getCountByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.eq(Dish::getCategoryId, categoryId);
        return this.count(dishWrapper);
    }

    /**
     * 菜品分页查询
     *
     * @param page     第几页
     * @param pageSize 每页显示条数
     * @param name     菜品名称
     * @return
     */
    @Override
    public Page<DishDto> dishPageList(Integer page, Integer pageSize, String name) {
        // 分页构造器
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        // 构造条件构造器
        MPJLambdaWrapper<Dish> dishWrapper = new MPJLambdaWrapper<Dish>()
                .selectAll(Dish.class)
                // 将 Category 表中的 name 映射到 DishDto 中的 categoryName
                .selectAs(Category::getName, DishDto::getCategoryName)
                .leftJoin(Category.class, Category::getId, Dish::getCategoryId)
                .like(StringUtils.isNotBlank(name), Dish::getName, name)
                .eq(Dish::getIsDeleted, ACTITVE.getValue())
                .orderByDesc(Dish::getUpdateTime);
        // 进行分页查询
        this.baseMapper.selectJoinPage(dishDtoPage, DishDto.class, dishWrapper);

        return dishDtoPage;
    }

    /**
     * 批量删除/批量启用/删除/启用
     *
     * @param status 状态
     * @param ids    菜品id，可用,分割
     * @return
     */
    @Override
    public boolean updateDishStatus(Integer status, String ids) {
        String[] idsArr = ids.split(",");
        List<Dish> dishes = new ArrayList<>();
        Arrays.stream(idsArr).forEach(id -> {
            Dish.DishBuilder builder = Dish.builder().id(Long.parseLong(id));
            // 0 停售 1 起售 2 删除
            if (status != 2) {
                builder.status(status);
            } else {
                builder.isDeleted(DELECTED.getValue());
            }
            dishes.add(builder.build());
        });
        return this.updateBatchById(dishes);

    }

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public boolean saveWithFlavor(DishDto dto) {
        if (dto.getId() == null) {
            // 新增菜品，状态默认为1
            dto.setStatus(1);
            dto.setCode("");
            // 保存菜品的基本信息到菜品表dish
            this.save(dto);

            // 保存口味数据到菜品口味表dish_flavor
            Long id = dto.getId();
            List<DishFlavor> flavors = dto.getFlavors();
            flavors.forEach(flavor -> flavor.setDishId(id));
            dishFlavorService.saveBatch(flavors);
        } else {
            this.updateById(dto);
            // 删除口味数据
            //清理当前菜品对应口味数据---dish_flavor表的delete操作（无论口味有没有发生修改，直接删除之前的重新插入）
            LambdaQueryWrapper<DishFlavor> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(DishFlavor::getDishId, dto.getId());
            dishFlavorService.remove(queryWrapper);

            //添加当前提交过来的口味数据---dish_flavor表的insert操作
            dto.getFlavors().forEach(flavor -> flavor.setDishId(dto.getId()));
            dishFlavorService.saveBatch(dto.getFlavors());
        }

        return true;
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getDishByIdWithFlavor(String id) {
        MPJLambdaWrapper<Dish> dishWrapper = new MPJLambdaWrapper<Dish>()
                .selectAll(Dish.class)
                .selectCollection(DishFlavor.class, DishDto::getFlavors)
                .leftJoin(DishFlavor.class, on -> on.eq(DishFlavor::getDishId, Dish::getId).eq(DishFlavor::getIsDeleted, ACTITVE.getValue()))
                .eq(Dish::getId, id)
                .eq(Dish::getIsDeleted, ACTITVE.getValue());

        return dishMapper.selectJoinOne(DishDto.class, dishWrapper);
    }

    /**
     * 根据分类id查询菜品列表
     * @param dish
     * @return
     */
    @Override
    public List<DishDto> getCategoryList(Dish dish) {
        MPJLambdaWrapper<Dish> dishWrapper = new MPJLambdaWrapper<Dish>()
                .selectAll(Dish.class)
                .selectCollection(DishFlavor.class, DishDto::getFlavors)
                .leftJoin(DishFlavor.class, on -> on.eq(DishFlavor::getDishId, Dish::getId).eq(DishFlavor::getIsDeleted, ACTITVE.getValue()))
                .eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .eq(Dish::getStatus, 1)
                .eq(Dish::getIsDeleted, ACTITVE.getValue())
                .orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        return dishMapper.selectJoinList(DishDto.class, dishWrapper);
    }
}
