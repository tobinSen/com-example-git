package com.example.spring.webservice.service.config;

import com.example.spring.webservice.service.service.DemoService;
import com.example.spring.webservice.service.service.DemoServiceImpl;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CxfConfig {

    //用于访问wsdl入口：http://localhost:8090/demo/api?wsdl
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/demo/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public DemoService demoService() {
        return new DemoServiceImpl();
    }

    //方式一：
    @Bean
    public Endpoint endpoint() {
        Endpoint endpoint = new EndpointImpl(springBus(), demoService());
        endpoint.publish("/api");
        return endpoint;
    }

    //方式二：
    @PostConstruct
    public void init() {
        //创建web服务工厂
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        //设置服务类
        factory.setServiceClass(DemoService.class);
        //设置对外发布服务地址
        factory.setAddress("http://localhost:8090/demo/api");
        //创建服务
        Server server = factory.create();
        //启动服务
        server.start();
    }

    //jax-rs 暴露
    @PostConstruct
    public void rs(){
        //发布REST任务
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress("http://localhost:8080/ws/rest");
        factory.setResourceClasses(Object.class);
        factory.setResourceProviders(null);
        factory.setProviders(null);
        factory.create();
    }

    //jax-rs 访问
    @PostConstruct
    public void rs_client(){
        String baseAddress = "http://localhost:8080/ws/rest";
        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new JacksonJsonProvider());


        List productList = WebClient.create(baseAddress, providerList)
                .path("/products").accept(MediaType.APPLICATION_JSON)
                .get(List.class);

    }
}
