package com.example.shardingsphere.snow.sharding.mapper;

import com.example.shardingsphere.snow.sharding.entity.OrderNewInfoEntity;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/3/23 10:52
 * @className OrderNewMapper
 * @desc 订单 new Mapper
 */
public interface OrderNewMapper {

    List<OrderNewInfoEntity> queryOrderInfoList(OrderNewInfoEntity orderInfo);

    OrderNewInfoEntity queryOrderInfoByOrderId(OrderNewInfoEntity orderInfo);

    int addOrder(OrderNewInfoEntity orderInfo);
}
