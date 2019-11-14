package com.example.spring.git.controller;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class AnoBean1 implements IBean {

    private String name = "ano order bean 1";

    public AnoBean1() {
        System.out.println(name);
    }
}

