package com.example.spring.rocketmq.producer.controller;

import com.example.spring.rocketmq.producer.producer.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class MqController {

    @Autowired
    private MqProducer mqProducer;

    @RequestMapping("/mq")
    public Map<String, Object> mqSend() throws Exception {
        mqProducer.sendMessage();
        return Collections.emptyMap();
    }

}
