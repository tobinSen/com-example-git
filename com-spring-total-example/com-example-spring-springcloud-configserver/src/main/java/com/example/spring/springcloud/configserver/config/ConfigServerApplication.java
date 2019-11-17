package com.example.spring.springcloud.configserver.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * /{application}/{profile}[/{label}]
 * /{application}-{profile}.yml
 * /{label}/{application}-{profile}.yml
 * /{application}-{profile}.properties
 * /{label}/{application}-{profile}.properties
 *
 * 访问：http://127.0.0.1:9999/spring-cloud-config-server/master
 * http://127.0.0.1:9999/spring-cloud-config-server/dev/master
 * http://127.0.0.1:9999/{应用名称}/{环境}/{分支}
 */

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient //注册到注册中心
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
