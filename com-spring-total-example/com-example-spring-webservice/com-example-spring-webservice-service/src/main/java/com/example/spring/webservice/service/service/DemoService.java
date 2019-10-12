package com.example.spring.webservice.service.service;

import javax.jws.WebService;

@WebService(name = "DemoService", // 暴露服务名称
        wsdlLocation = "http://localhost:8090/demo/api?wsdl", //wsdl访问入口
        targetNamespace = "http://service.service.webservice.spring.example.com"// 命名空间,一般是接口的包名倒序
)
public interface DemoService {

    public String sayHello(String user);

}
