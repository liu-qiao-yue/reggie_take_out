package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.itheima.reggie.dto.OrdersDto;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.OrdersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    /**
     * 订单分页查询
     * @param page 第几页
     * @param pageSize 每页显示几条
     * @return Page
     */
    @Override
    public Page<OrdersDto> pageList(Integer page, Integer pageSize,
                                    String number, String beginTime,
                                    String endTime) {
        // 分页构造器
        Page<OrdersDto> ordersDtoPage = new Page<>(page, pageSize);

        // 构造条件构造器
        MPJLambdaWrapper<Orders> wrapper = new MPJLambdaWrapper<Orders>()
                .selectAll(Orders.class)
                .selectAs(User::getName, OrdersDto::getUserName)
                .selectAs(User::getPhone, OrdersDto::getPhone)
                .selectAs(AddressBook::getDetail, OrdersDto::getAddress)
                .selectAs(AddressBook::getConsignee, OrdersDto::getConsignee)
                .leftJoin(User.class, User::getId, Orders::getUserId)
                .leftJoin(AddressBook.class, AddressBook::getId, Orders::getAddressBookId)
                .like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime)
                .lt(StringUtils.isNotEmpty(endTime), Orders::getOrderTime, endTime);

        return this.baseMapper.selectJoinPage(ordersDtoPage, OrdersDto.class, wrapper);
    }
}
