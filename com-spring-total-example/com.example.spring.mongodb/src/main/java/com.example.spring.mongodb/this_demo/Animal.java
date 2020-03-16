package com.example.spring.mongodb.this_demo;

public class Animal {

    private Object obj;
    private String name;

    public Animal(Object obj, String name) {
        this.obj = obj;
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
