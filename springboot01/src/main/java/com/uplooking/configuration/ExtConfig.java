package com.uplooking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 扩展原理：
 * BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作
 * BeanFactoryPostProcessor：beanFactory的后置处理器
 * 在BeanFactory标准初始化之后调用，所有的bean定义已经保存加载到beanFactory,但是bean的实例还没有创建。
 * BeanDefinitionRegistryPostProcessor extend BeanFactoryPostProcessor
 * 在所有bean定义信息将要被加载，bean实例还未创建的
 * <p>
 * BeanDefinitionRegistryPostProcessor 优先于 BeanFactoryPostProcessor 可以利用这向容器中额外添加组件
 */

@Configuration
@EnableWebMvc
public class ExtConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views", ".jsp");
    }
}
