package com.example.spring.dubbo.provider.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.spring.dubbo.provider.provider.Provider;

@Service
public class ProviderImpl implements Provider {

    @Override
    public void provider(String msg) {
        System.out.println("provider生产了：" + msg);
    }
}
