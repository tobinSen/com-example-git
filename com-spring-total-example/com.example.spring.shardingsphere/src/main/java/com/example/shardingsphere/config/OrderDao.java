package com.example.shardingsphere.config;


import java.util.List;

public interface OrderDao {

    List<Order> getOrderListByUserId(Integer userId);

    void createOrder(Order order);
}
