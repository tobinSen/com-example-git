package com.example.spring.hutool.easyMapper;

import com.baidu.unbiz.easymapper.MapperFactory;

public class EasyMapperDemo {

    public static void main(String[] args) {
        Person p = new Person();
        PersonDto personDto = MapperFactory.getCopyByRefMapper()
                .mapClass(Person.class, PersonDto.class)
                .registerAndMap(p, PersonDto.class);
    }

}
