package com.example.spring.email;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RestController
public class EmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @RequestMapping("login.do")
    public String test(@RequestBody JSONObject json, String name, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie("name", name));
        response.addCookie(new Cookie("id", UUID.randomUUID().toString()));

        return name;
    }

    @RequestMapping("test.do")
    public String test1(@RequestParam("idList") List<Long> idList, String name, HttpServletResponse response) throws Exception {
        response.addCookie(new Cookie("name", name));
        response.addCookie(new Cookie("id", UUID.randomUUID().toString()));

        return name;
    }
}
