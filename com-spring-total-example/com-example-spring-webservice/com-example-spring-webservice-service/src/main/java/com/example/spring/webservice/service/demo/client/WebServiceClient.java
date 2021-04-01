package com.example.spring.webservice.service.demo.client;

import javax.xml.rpc.ServiceException;
import java.io.IOException;

public class WebServiceClient {

    public static void main(String[] args) throws ServiceException, IOException {
        DemoServiceImplService factory = new DemoServiceImplServiceLocator();
        DemoServiceImpl service = factory.getDemoServiceImplPort();
        for (int i = 0; i < 10; i++) {
            service.sayHello("tong");
        }

        System.in.read();
    }
}
