package com.uplooking.factory;

import com.uplooking.pojo.Person;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Person> {

    //返回一个person对象，这个对象会添加到Ioc容器中
    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 是否为单例
     * true :容器中只会保留一份
     * false:多例：每次调用都会创建
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
