package com.example.spring.dubbo.consumer.controller;

import com.example.spring.dubbo.consumer.consumer.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private Consumer consumer;

    @RequestMapping("consumer")
    public Map<String, Object> consumer() throws Exception {
        System.out.println("start consumer with controller");
        consumer.consumer("consumerController stat");
        return Collections.emptyMap();
    }
}
