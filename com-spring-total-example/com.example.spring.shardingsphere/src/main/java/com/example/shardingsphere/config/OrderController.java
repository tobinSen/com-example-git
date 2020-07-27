package com.example.shardingsphere.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    @RequestMapping(path = "/createOrder/{userId}/{orderId}", method = {RequestMethod.GET})
    public String createOrder(@PathVariable("userId") Integer userId, @PathVariable("orderId") Integer orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);
        orderDao.createOrder(order);
        return "success";
    }

    @RequestMapping(path = "/{userId}", method = {RequestMethod.GET})
    public List<Order> getOrderListByUserId(@PathVariable("userId") Integer userId) {
        return orderDao.getOrderListByUserId(userId);
    }
}
