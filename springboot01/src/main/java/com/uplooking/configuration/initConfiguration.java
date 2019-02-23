package com.uplooking.configuration;

import com.uplooking.pojo.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScans(value = {
        @ComponentScan(value = "com.uplooking",
                excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})}),
        @ComponentScan(value = "com.uplooking",
                includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)}, useDefaultFilters = false)
})

public class initConfiguration {

    /**
     * 1.包扫描：扫描的是标记了注解的@Controller @Component @Repository
     * 2.bean只是产生了id,并没有注入到容器中
     * 3.包扫描 + 注解标识
     * 4.xml中的扫描指定的是包路径下 + 实例化Bean
     * 5.SpringBoot中配置类：xml+注解版
     */

    @Bean //@Bean 只是产生了bean的id 但是并没有注入到容器中 1.包扫描 2.主动注入
    public Person getPerson() {
        Person person = new Person();
        person.setName("张三");
        System.out.println("1233");
        return person;
    }

    @Bean
    public MyBeanDefinitionRegistryPostProcessor MyBeanDefinitionRegistryPostProcessor() {
        return new MyBeanDefinitionRegistryPostProcessor();
    }
}
