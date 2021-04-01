/**
 * DemoServiceImplServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.spring.webservice.service.demo.client;

public class DemoServiceImplServiceTestCase extends junit.framework.TestCase {
    public DemoServiceImplServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testDemoServiceImplPortWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.example.spring.webservice.service.demo.client.DemoServiceImplServiceLocator().getDemoServiceImplPortAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.example.spring.webservice.service.demo.client.DemoServiceImplServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1DemoServiceImplPortSayHello() throws Exception {
        com.example.spring.webservice.service.demo.client.DemoServiceImplPortBindingStub binding;
        try {
            binding = (com.example.spring.webservice.service.demo.client.DemoServiceImplPortBindingStub)
                          new com.example.spring.webservice.service.demo.client.DemoServiceImplServiceLocator().getDemoServiceImplPort();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        binding.sayHello(new java.lang.String());
        // TBD - validate results
    }

}
