package com.example.spring.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("hello")
    @PreAuthorize("hasAuthority('admin')")
    public String hello() {
        return "hello spring security";
    }
}
