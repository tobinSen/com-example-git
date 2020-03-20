package com.example.spring.hutool.config;

import com.example.spring.hutool.core.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConfigA.class}) //配置类中的Bean不能跨文件
public class ConfigB {

    @Bean
    public Cat c(Cat cat) {
        return cat;
    }
}
