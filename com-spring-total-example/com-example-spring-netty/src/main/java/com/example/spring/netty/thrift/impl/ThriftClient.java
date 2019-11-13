package com.example.spring.netty.thrift.impl;

import com.example.spring.netty.thrift.Person;
import com.example.spring.netty.thrift.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {

    public static void main(String[] args) throws Exception {
        //1.客户端传输
        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        //2.客户端协议
        TProtocol protocol = new TCompactProtocol(tTransport);
        //3.客户端【这里就相当于是在本地调用一样，但是实际上是通过socket来进行传输的】
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();

            Person person = client.getPersionByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());
            System.out.println("---------------------");

            Person person1 = new Person();
            person1.setAge(12);
            person1.setUsername("李四");
            person1.setMarried(true);

            client.savePerson(person);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tTransport.close();
        }
    }

}
