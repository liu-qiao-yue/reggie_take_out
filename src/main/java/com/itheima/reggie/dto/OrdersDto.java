package com.itheima.reggie.dto;

import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersDto extends Orders {

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户电话
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 收货人
     */
    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
