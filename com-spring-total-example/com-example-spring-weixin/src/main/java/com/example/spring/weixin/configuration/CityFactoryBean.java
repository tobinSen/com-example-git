package com.example.spring.weixin.configuration;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class CityFactoryBean implements FactoryBean<City>, InitializingBean, DisposableBean {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Environment environment;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;
    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    private City city;

    //step 2
    @Override
    public City getObject() throws Exception {
        String applicationName = applicationContext.getApplicationName();
        String[] activeProfiles = environment.getActiveProfiles();
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();
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
