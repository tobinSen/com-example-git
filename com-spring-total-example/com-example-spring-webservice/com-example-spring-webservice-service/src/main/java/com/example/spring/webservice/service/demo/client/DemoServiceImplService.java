/**
 * DemoServiceImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.spring.webservice.service.demo.client;

public interface DemoServiceImplService extends javax.xml.rpc.Service {
    public java.lang.String getDemoServiceImplPortAddress();

    public com.example.spring.webservice.service.demo.client.DemoServiceImpl getDemoServiceImplPort() throws javax.xml.rpc.ServiceException;

    public com.example.spring.webservice.service.demo.client.DemoServiceImpl getDemoServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
