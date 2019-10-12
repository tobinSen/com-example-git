package com.example.spring.webservice.client.config;

import com.example.spring.webservice.client.service.DemoWebService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;


//JaxWsProxyFactoryBean--->客户端代码可以通过wdsl来生成
//生成指令

/**
 * wsimport -s /Users/tongjian/Downloads/ -keep -p com.example.spring.webservice.client -verbose -Xnocompile http://localhost:8090/demo/api?wsdl
 */
@Configurable
public class WSConfiguration {

    @Bean
    public DemoWebService ofcWebService() {
        // 接口地址
//        String address = "http://192.168.60.59:8080/webservice/wsdl/wumart/so/push?wsdl";
        // http://192.168.60.151:8080/webservice/wsdl/wumart/so/push?wsdl
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress("");
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(DemoWebService.class);
        // 创建一个代理接口实现
        return (DemoWebService) jaxWsProxyFactoryBean.create();
    }
}
