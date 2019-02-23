package com.uplooking.configuration;

import com.uplooking.pojo.Dog;
import com.uplooking.pojo.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        System.out.println();
        return person;
    }

    @Bean
    public Dog getDog(Person person) {
        Dog dog = new Dog();
        dog.setName(person.getName());
        return dog;
    }
}
