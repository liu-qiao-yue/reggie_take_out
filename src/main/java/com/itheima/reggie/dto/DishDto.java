package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ellie
 */
@Data
public class DishDto extends Dish {//继承了Dish，即为该对象拥有了Dish的所有属性

    /**
     * list集合用于接收前端传输过来的flavors数据，flavors里面的每个对象对应的是DishFlavor实体
     * 用于封装菜品对应的口味集合信息
     */
    private List<DishFlavor> flavors = new ArrayList<>();

    /**
     * 菜品分类名称
     */
    private String categoryName;

    private Integer copies;
}