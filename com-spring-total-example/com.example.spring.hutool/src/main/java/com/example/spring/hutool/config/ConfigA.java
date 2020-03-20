package com.example.spring.hutool.config;

import com.example.spring.hutool.core.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ConfigA {

    @Bean("cat")
    public Cat cat() {
        return new Cat();
    }

}
