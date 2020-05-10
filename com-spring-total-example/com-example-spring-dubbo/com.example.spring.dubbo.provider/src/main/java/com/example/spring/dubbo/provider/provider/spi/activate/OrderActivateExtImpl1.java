package com.example.spring.dubbo.provider.provider.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

@Activate(order = 2, group = {"order"})
public class OrderActivateExtImpl1 implements ActivateExt1 {

    public String echo(String msg) {
        System.out.println("order");
        return msg;
    }
}