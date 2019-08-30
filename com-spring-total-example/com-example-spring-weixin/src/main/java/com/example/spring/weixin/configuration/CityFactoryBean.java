package com.example.spring.weixin.configuration;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CityFactoryBean implements FactoryBean<City>, InitializingBean, DisposableBean {

    private City city;

    //step 2
    @Override
    public City getObject() throws Exception {
        return city;
    }

    @Override
    public Class<?> getObjectType() {
        return City.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {

    }

    //step 1
    @Override
    public void afterPropertiesSet() throws Exception {
        city = new City();
        city.setName("北京");
        city.setLocation("北京");
    }
}
