package com.example.spring.dubbo.consumer.consumer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.spring.dubbo.consumer.consumer.Consumer;
import com.example.spring.dubbo.provider.provider.Provider;
import org.springframework.stereotype.Service;

@Service
public class ConsumerImpl implements Consumer {

    @Reference
    private Provider provider;

    @Override
    public void consumer(String message) throws Exception {
        System.out.println("dubbo-consumer开始消费：" + message);
        provider.provider(message);
    }
}
