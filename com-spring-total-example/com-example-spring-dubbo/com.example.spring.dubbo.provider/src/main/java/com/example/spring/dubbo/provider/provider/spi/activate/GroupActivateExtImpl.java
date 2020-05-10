package com.example.spring.dubbo.provider.provider.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

@Activate(group = {"group1", "group2"})
public class GroupActivateExtImpl implements ActivateExt1 {

    public String echo(String msg) {
        System.out.println("group1 group2");
        return msg;
    }
}