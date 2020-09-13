package com.example.spring.hutool;

import cn.hutool.core.date.DateTime;
import com.example.spring.hutool.core.Cat;
import org.springframework.boot.context.properties.PropertyMapper;

public class PropertyMapperDemo {

    public String name = "tongjian";


    public static void main(String[] args) {
        Cat from = new Cat() {{
            setAge(1);
//            setName("tongjian");
            setCreateTime(new DateTime());
        }};
        System.out.println(from);

        PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

        Cat to = new Cat();
        mapper.from(from::getName).toCall(() -> System.out.println("12"));

        int i = 1 | 2;
        System.out.println(i);

    }
}
