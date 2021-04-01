package com.example.spring.webservice.service.demo.service;



import javax.xml.ws.Endpoint;
import java.io.IOException;

public class WebServicePublish {

    public static void main(String[] args) throws IOException {
        Endpoint.publish("http://localhost:8080/webservice", new DemoServiceImpl());

        System.out.println("webService publish success ");
        System.in.read();
    }
}
