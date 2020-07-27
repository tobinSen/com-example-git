package com.example.spring.apollo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Configuration
@ConfigurationProperties(prefix = "person")
public class Person {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
