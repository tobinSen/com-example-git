package com.example.spring.git.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BServiceImpl implements BService {

    @Autowired
    private AService aService;

    @Override
    public void b() {
        System.out.println("b");
        aService.a();
    }
}


