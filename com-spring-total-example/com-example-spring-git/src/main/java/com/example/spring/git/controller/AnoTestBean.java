package com.example.spring.git.controller;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnoTestBean {

    /**
     * 1.@Order只支持AOP切面的顺序
     * 2.形参支持集合注入@Order支持注入的顺序
     */
    public AnoTestBean(List<IBean> anoBeanList) {
        for (IBean bean : anoBeanList) {
            System.out.println("in ano testBean: " + bean.getClass().getName());
        }
    }
}
