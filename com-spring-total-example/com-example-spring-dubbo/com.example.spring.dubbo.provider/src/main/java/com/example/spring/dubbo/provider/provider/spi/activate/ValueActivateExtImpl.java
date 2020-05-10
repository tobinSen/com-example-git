package com.example.spring.dubbo.provider.provider.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

@Activate(value = {"value1"}, group = {"value"})
public class ValueActivateExtImpl implements ActivateExt1 {

    public String echo(String msg) {
        System.out.println("value1 value");
        return msg;
    }
}
