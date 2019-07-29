package com.example.spring.apollo.controller;

import com.example.spring.apollo.config.ConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApolloController {

    @Autowired
    private ConfigTest configTest;

    @RequestMapping("apollo.do")
    public String apollo() {
        return configTest.getJavaApollo();
    }

}
