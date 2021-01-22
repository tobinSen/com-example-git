package com.example.shardingsphere.snow.service;

import com.example.shardingsphere.snow.entity.OrderInfo;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/3 22:39
 * @className OrderService
 * @desc
 */
public interface OrderService {

    List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    int addOrder(OrderInfo orderInfo);
}
