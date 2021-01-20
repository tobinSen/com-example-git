package com.example.spring.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphqlApplication {
    public static void main(String [] args ) {
        new SpringApplication(GraphqlApplication.class).run(args);
    }
}
