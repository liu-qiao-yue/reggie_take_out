package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 *  添加菜品功能前端请求参数示例：
 *      "name": "我是菜品名称",
 *      "price": 999000,
 *      "code": "",
 *      "image": "b81c6947-9ac2-44e1-b481-e0ea5fa559da.png",
 *      "description": "我是添加菜品测试",
 *      "status": 1,
 *      "categoryId": "1847227623393320961",
 *      "flavors": [
 *          {
 *              "name": "甜味",
 *              "value": "[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]",
 *              "showOption": false
 *          }
 *      ]
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