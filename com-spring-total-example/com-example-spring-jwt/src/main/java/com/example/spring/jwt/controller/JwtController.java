package com.example.spring.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.spring.jwt.commont.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("jwt")
@Log4j2
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("token")
    public JSONObject jwtValidate(HttpServletRequest request) throws Exception {
        try {
            String username = request.getParameter("username");
            JSONObject json = new JSONObject();
            json.put("token", jwtUtil.generateToken(username));
            return json;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
