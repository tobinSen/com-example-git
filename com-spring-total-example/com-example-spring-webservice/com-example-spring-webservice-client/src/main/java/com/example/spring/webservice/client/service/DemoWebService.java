package com.example.spring.webservice.client.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "DemoService", // 暴露服务名称
        targetNamespace = "http://service.service.webservice.spring.example.com"// 命名空间,一般是接口的包名倒序
)
public interface DemoWebService {

    @WebMethod
    @WebResult(name = "webResult", targetNamespace = "")
    public String sayHello(@WebParam(name = "user") String user);
}
