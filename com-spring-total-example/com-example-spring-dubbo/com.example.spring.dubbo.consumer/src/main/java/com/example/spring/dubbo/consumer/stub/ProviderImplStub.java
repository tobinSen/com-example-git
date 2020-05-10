package com.example.spring.dubbo.consumer.stub;

import com.example.spring.dubbo.provider.provider.Provider;

public class ProviderImplStub implements Provider {
    @Override
    public void provider(String msg) {
        System.out.println("本地存根");
    }
}
