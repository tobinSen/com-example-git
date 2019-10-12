package com.example.spring.webservice.service.service;

import javax.jws.WebService;
import java.util.Date;

@WebService(serviceName = "DemoService", // 与接口中指定的name一致
        wsdlLocation = "http://localhost:8090/demo/api?wsdl", //wsdl访问入口
        targetNamespace = "http://service.service.webservice.spring.example.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.example.spring.webservice.service.service.DemoService"// 接口地址
)
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String user) {
        return user + "，现在时间：" + "(" + new Date() + ")";
    }

}

