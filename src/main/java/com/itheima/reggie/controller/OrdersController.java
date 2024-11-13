package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ellie
 */
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * 订单分页查询
     * @param page 第几页
     * @param pageSize 每页显示几条
     * @param number 订单号
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GetMapping("/page")
    public R<Page<OrdersDto>> pageList(Integer page, Integer pageSize,
                                       String number, String beginTime,
                                       String endTime) {
        return R.success(ordersService.pageList(page, pageSize, number, beginTime, endTime));
    }
}
