package com.uplooking.sentinel;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.config.WebServletConfig;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
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

    /*@ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint(SentinelConstants.PROPERTY_PREFIX)
    @Bean
    public SentinelEndpoint sentinelEndPoint() {
        return new SentinelEndpoint();
    }*/

    @PostConstruct
    public void init() {
        //dubbo消费者注册fallback
        DubboFallbackRegistry.setConsumerFallback((invoker, invocation, ex) -> {
            return new RpcResult(invoker.getInterface().getName() + "-" + invocation.getMethodName() + "-" + ex.getRuleLimitApp());
        });
        //dubbo生产者注册fallback
        DubboFallbackRegistry.setProviderFallback((invoker, invocation, ex) -> {
            return new RpcResult(invoker.getInterface().getName() + "-" + invocation.getMethodName() + "-" + ex.getRuleLimitApp());
        });
    }

    @ConditionalOnMissingBean
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public Converter<String, List<FlowRule>> flowRuleConverter() {
        return source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        });
    }

    @Bean
    public Converter<String, List<DegradeRule>> degradeRuleConverter() {
        return source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
        });
    }

    @Bean
    public Converter<String, List<AuthorityRule>> authorityRuleConverter() {
        return source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
        });
    }

    @Bean
    public Converter<String, List<SystemRule>> systemRuleConverter() {
        return source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
        });
    }

    @Bean
    public MySentinelEndpoint mySentinelEndpoint() {
        return new MySentinelEndpoint();
    }
}
