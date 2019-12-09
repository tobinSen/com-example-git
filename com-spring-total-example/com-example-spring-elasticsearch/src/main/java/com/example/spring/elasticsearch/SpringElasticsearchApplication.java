package com.example.spring.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * skyWalking
 * java
 * -javaagent:D:\installpackage\apache-skywalking-apm-6.5.0\apache-skywalking-apm-bin\agent\skywalking-agent.jar -Dskywalking.agent.service_name=springboot-elasticsearch -Dskywalking.collector.backend_service=localhost:11800
 */
@SpringBootApplication
public class SpringElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringElasticsearchApplication.class, args);
    }
}
