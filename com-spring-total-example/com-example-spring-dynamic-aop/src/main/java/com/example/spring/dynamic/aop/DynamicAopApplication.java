package com.example.spring.dynamic.aop;

import com.example.spring.dynamic.aop.importAware.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRedissonHttpSession
@EnableRetry
@EnableAspectJAutoProxy  //spring-boot-starter-aop 会自动开启，不需要添加注解
public class DynamicAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicAopApplication.class, args);
    }

}
