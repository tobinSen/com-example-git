package com.example.spring.alibaba.nacos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

/**
 *
 * spring-cloud-common
 *
 * LoadBalancerClient
 *      RibbonLoadBalancerClient     --> 算法
 *      BlockingLoadBalancerClient   --> Reactive
 *
 * @LoadBalanced
 *
 * @RibbonClient
 * @LoadBalancerClient
 */
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }

}
