package com.example.spring.pay;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PayController {


    @RequestMapping("pay/{name}/{id}")
    public Map<String, Object> pay(@PathVariable String name, @PathVariable Integer id) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        build();
        return map;
    }

    public void build() throws Exception {
        //mappingName是Controller的大写#方法名
        /**
         * fromMappingName:/pay/
         * fromController:http://localhost:8080/
         * fromMethodName:http://localhost:8080/pay/tongjian/1
         */
        String s = MvcUriComponentsBuilder.fromMappingName("PC#pay").build();
        System.out.println("fromMappingName:" + s);

        String s1 = MvcUriComponentsBuilder.fromController(PayController.class).build().encode().toUriString();
        System.out.println("fromController:" + s1);

        String s2 = MvcUriComponentsBuilder.fromMethodName(PayController.class, "pay", "tongjian", 1).build().toUriString();
        System.out.println("fromMethodName:" + s2);


    }

}
