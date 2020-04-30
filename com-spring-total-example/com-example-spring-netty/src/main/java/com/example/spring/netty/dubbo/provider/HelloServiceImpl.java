package com.example.spring.netty.dubbo.provider;

import com.example.spring.netty.dubbo.HelloService;

public class HelloServiceImpl implements HelloService {


    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息：" + mes);
        if (null != mes) {
            return "你好客户端，我已经收到了消息[" + mes + "]";
        }
        return "你好客户端，我没有收到消息";

    }
}
