package com.example.spring.springcloudgray;

import cn.springcloud.gray.server.EnableGrayServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGrayServer
//@EnableDiscoveryClient
public class SpringCloudGrayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGrayApplication.class, args);
    }

}
