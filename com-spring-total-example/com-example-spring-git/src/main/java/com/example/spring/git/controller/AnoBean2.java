package com.example.spring.git.controller;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class AnoBean2 implements IBean {

    private String name = "ano order bean 2";

    public AnoBean2() {
        System.out.println(name);
    }
}

