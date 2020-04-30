package com.example.spring.netty.dubbo.consumer;

import com.example.spring.netty.dubbo.HelloService;

public class ClientBootStrap {

    private static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {

        NettyClient customer = new NettyClient();

        HelloService helloService = (HelloService) customer.getBean(HelloService.class, providerName);
        String result = helloService.hello("你好，dubbo");
        System.out.println("返回的结果：" + result);

    }

}
