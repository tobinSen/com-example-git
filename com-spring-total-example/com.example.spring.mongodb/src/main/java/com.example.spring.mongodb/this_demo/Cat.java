package com.example.spring.mongodb.this_demo;

import java.util.UUID;

public class Cat {

    public void cat() {
        Animal animal = new Animal(this, UUID.randomUUID().toString().replace("-", ""));
        System.out.println(animal.getName());
        System.out.println(animal.getObj());
    }
}
