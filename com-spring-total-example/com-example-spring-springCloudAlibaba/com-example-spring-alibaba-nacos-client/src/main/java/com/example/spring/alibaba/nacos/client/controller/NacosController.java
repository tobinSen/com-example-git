package com.example.spring.alibaba.nacos.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("nacos")
public class NacosController {


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("client.do")
    public Map<String, Object> nacosClient(@RequestParam("name") String name) throws Exception {
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-server");

        String url = serviceInstance.getUri() + "/nacos/server.do?name=" + name;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(new URI(url), Map.class);
        Map map = forEntity.getBody();
        return map;
    }


}
