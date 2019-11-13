package com.example.spring.netty.thrift.impl;

import com.example.spring.netty.thrift.DataException;
import com.example.spring.netty.thrift.Person;
import com.example.spring.netty.thrift.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersionByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param:" + username);

        Person person = new Person();
        person.setAge(1);
        person.setUsername(username);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {

        System.out.println("Got Client Param:");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
