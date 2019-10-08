package com.example.spring.weixin.controller;

import com.example.spring.weixin.common.ThisTest;
import com.example.spring.weixin.configuration.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class WeixinController {

    @Autowired
    private City city;
    @Autowired
    private ThisTest thisTest;

    @RequestMapping("ajax.do")
    public Map<String, Object> weixin() throws Exception {
        try {
            thisTest.thisTest();
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
