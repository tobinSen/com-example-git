package com.example.spring.redis.controller;

import com.example.spring.redis.provider.RedisMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
public class RedisMqController {

    @Autowired
    private RedisMqProvider redisMqProvider;

    @RequestMapping("send")
    public Map<String, Object> send() throws Exception {
        try {
            redisMqProvider.sendMessage("redis-mq-send");
            return Collections.emptyMap();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
