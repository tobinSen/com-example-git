package com.example.spring.git.parent;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class Person {
    @Autowired
    private Cat cat;

    public Person() {
        System.out.println(cat);
    }

    @PostConstruct
    public void like(){
        System.out.println(cat);  //这个优先进行
    }
}
