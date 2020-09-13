package com.example.spring.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 1.当我文件内有相同的字段的时候，是基于激活【文件的来进行覆盖】的和【环境的覆盖】
 * 2.--spring.profiles.active=dev 的优先级大于application.yml中的优先级
 * 总结：4，3
 */
@Configuration
public class ProfileConfig {

    @Value("${id}")
    private Integer id;

    @Value("${name}")
    private String name;

    @Value("${age}")
    private Integer age;

//    @Value("${address}")
//    private String address;

    @PostConstruct
    public void init() {
        System.out.println(id + "--" + name + "--" + age + "--");
    }
}
