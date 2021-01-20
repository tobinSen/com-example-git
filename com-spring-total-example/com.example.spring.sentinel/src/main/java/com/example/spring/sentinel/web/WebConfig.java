package com.example.spring.sentinel.web;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.adapter.servlet.config.WebServletConfig;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfig {

    // 注册sentinel拦截器
    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        // 1. servlet被阻塞时候返回的页面，重定向
        WebServletConfig.setBlockPage("blockPage.html");  // 这里是阻塞队列的时候

        // 2. 清洗一下资源
        WebCallbackManager.setUrlCleaner(new UrlCleaner() {
            @Override
            public String clean(String originUrl) {
                if (originUrl == null || originUrl.isEmpty()) {
                    return originUrl;
                }

                // 比如将满足 /foo/{id} 的 URL 都归到 /foo/*
                if (originUrl.startsWith("/foo/")) {
                    return "/foo/*";
                }
                // 不希望统计 *.ico 的资源文件，可以将其转换为 empty string (since 1.6.3)
                if (originUrl.endsWith(".ico")) {
                    return "";
                }
                return originUrl;
            }
        });

        // 3. 当阻塞的时候的处理器
        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
            @Override
            public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {

            }
        });

        // 4. 解析源资源
        WebCallbackManager.setRequestOriginParser(new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest request) {
                return request.getHeader("origin");
            }
        });


        return registration;
    }
}
