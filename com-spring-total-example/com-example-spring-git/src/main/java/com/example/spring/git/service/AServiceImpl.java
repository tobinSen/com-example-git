package com.example.spring.git.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AServiceImpl implements AService {

    @Autowired
    private BService bService;


    @Override
    public void a() {
        System.out.println("a");
        bService.b();
    }
}


