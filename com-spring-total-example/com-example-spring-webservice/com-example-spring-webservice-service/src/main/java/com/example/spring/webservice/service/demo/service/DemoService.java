package com.example.spring.webservice.service.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface DemoService {

    @WebMethod
    void sayHello(String name);

}
