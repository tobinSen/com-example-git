package com.example.spring.layui.config;

public interface MyInterface {

    default void sayHello() {

    }

    static void sayHello(String name) {

    }
}
