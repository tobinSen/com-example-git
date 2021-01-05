package com.example.spring.git.parent;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Tom extends Person{

    public Tom() {
        super();
    }

    @PostConstruct
    public void like2(){
        System.out.println("tom"); //这个第二个运行
    }
}
