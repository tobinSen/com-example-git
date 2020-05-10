package com.example.spring.dubbo.provider.provider.spi.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("dubbo") //默认配置
public interface AdaptiveExt2 {

    @Adaptive("key")
    String echo(String msg, URL url); //必须要有URl参数
}
