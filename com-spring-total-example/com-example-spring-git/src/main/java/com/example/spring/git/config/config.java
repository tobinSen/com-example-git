package com.example.spring.git.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {


    @Bean
    public ServiceA serviceA() {
        ServiceC serviceC = serviceC();
        System.out.println(serviceC);
        return new ServiceA(serviceC);
    }

    @Bean
    public ServiceB serviceB() {
        ServiceC serviceC = serviceC();
        System.out.println(serviceC);
        return new ServiceB(serviceC);
    }

    @Bean
    ServiceC serviceC() {
        return new ServiceC();
    }
}
