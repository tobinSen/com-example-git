package com.example.spring.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 总结：4，3，3，3（dataId,group refresh namespace）
 * 多环境
 * 多配置加载
 * 共享配置
 */
@SpringBootApplication
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }

}
