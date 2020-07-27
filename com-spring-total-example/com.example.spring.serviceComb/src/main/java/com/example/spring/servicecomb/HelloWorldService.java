package com.example.spring.servicecomb;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "hello")
@RequestMapping("/")
public class HelloWorldService {


    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }
}