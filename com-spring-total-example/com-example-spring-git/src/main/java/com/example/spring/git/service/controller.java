package com.example.spring.git.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @Autowired
    private AService aService;

    @Autowired
    private BService bService;

    @RequestMapping("test.do")
    public void test() {
        aService.a();
        bService.b();
    }

}
