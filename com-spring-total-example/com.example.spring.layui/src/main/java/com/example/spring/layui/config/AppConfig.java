package com.example.spring.layui.config;

import com.example.spring.layui.reflect.Animal;
import com.example.spring.layui.reflect.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public String nameStr= "tonjain";

    @Bean
    public Animal animal() {
        return new Animal();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }


}
