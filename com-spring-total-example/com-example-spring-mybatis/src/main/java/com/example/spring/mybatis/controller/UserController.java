package com.example.spring.mybatis.controller;

import com.example.spring.mybatis.dao.UserMapper;
import com.example.spring.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("user.do")
    public Map<String, Object> user() throws Exception {
        List<User> users = userMapper.selectAllUser();
        return Collections.emptyMap();
    }

    @RequestMapping("add.do")
    public Map<String, Object> add(@RequestBody User user) throws Exception {
        userMapper.insert(user);
        return Collections.emptyMap();
    }
}
