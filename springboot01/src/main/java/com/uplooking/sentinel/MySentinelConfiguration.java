package com.uplooking.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.config.WebServletConfig;
import com.google.common.collect.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.List;

public class MySentinelConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        /**
         * 自定义Filter：
         *      order：过滤器的顺序
         *      String[] urlPatten:表示拦截器的目录
         *      blockUrl：堵塞的目录
         */
        String blockUrl = "";
        List<String> urlPattern = Lists.newArrayList();
        int order = Integer.MAX_VALUE;
        WebServletConfig.setBlockPage(blockUrl);
        Filter filter = new CommonFilter();//自定义过滤器

        //注册过滤器
        registrationBean.setFilter(filter);
        //匹配拦截路径
        registrationBean.setUrlPatterns(urlPattern);
        //这个过滤器链的顺序
        registrationBean.setOrder(order);
        return registrationBean;
    }

}
