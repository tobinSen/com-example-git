package com.example.spring.alibaba.nacos.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("nacos")
public class NacosController {

    @RequestMapping("server.do")
    public Map<String, Object> nacosServer(@RequestParam("name") String name) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID());
        map.put("name", name);
        map.put("time", new Date());
        return map;
    }

}
