package com.example.spring.weixin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class WeixinController {

    @RequestMapping("weixin.do")
    public Map<String, Object> weixin() throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", "123");
            map.put("code", "123");
            return map;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
