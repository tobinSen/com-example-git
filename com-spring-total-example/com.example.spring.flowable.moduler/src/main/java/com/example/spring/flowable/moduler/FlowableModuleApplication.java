package com.example.spring.flowable.moduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(/*scanBasePackages = {"org.flowable.ui.modeler","org.flowable.ui.common"}*/)
public class FlowableModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableModuleApplication.class, args);
    }
}
