package com.example.spring.git.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class LoginController {

    @RequestMapping("login.do")
    public String test(@RequestParam("ids") List<Long> ids ,String name, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie("name", name));
        response.addCookie(new Cookie("id", UUID.randomUUID().toString()));

        return name;
    }

}
