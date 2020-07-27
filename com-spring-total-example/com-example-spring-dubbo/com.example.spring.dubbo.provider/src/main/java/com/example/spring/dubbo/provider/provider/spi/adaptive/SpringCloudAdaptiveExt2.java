package com.example.spring.dubbo.provider.provider.spi.adaptive;

import com.alibaba.dubbo.common.URL;

public class SpringCloudAdaptiveExt2 implements AdaptiveExt2 {

    @Override
    public String echo(String msg, URL url) {
        System.out.println("SpringCloud");
        return "SpringCloud";
    }
}
