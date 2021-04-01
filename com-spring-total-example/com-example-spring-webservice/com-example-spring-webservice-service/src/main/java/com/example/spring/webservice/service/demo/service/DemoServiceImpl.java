package com.example.spring.webservice.service.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;

@WebService
public class DemoServiceImpl implements DemoService {

    @WebMethod
    @Override
    public void sayHello(String name) {
        System.out.println(name + "，现在时间：" + "(" + new Date() + ")");
    }
}
