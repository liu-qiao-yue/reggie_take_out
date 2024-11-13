package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     * 订单分页查询
     * @param page 第几页
     * @param pageSize 每页显示几条
     * @return Page
     */
    Page<OrdersDto> pageList(Integer page, Integer pageSize,
                             String number, String beginTime,
                             String endTime);
}
