package com.example.spring.dynamic.aop.config;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ServiceImpl {

    @PostConstruct
    public void t() {
        test();
    }

    public void test() {

        System.out.println("前置通知Test");
    }

}
