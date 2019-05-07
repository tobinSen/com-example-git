package com.uplooking.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class ServletConfig {
    /**
     * ServletRegistrationBean, FilterRegistrationBean, ServletListenerRegistrationBean
     */
    @Bean
    public ServletRegistrationBean registerServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new RegisterServlet(), "/registerServlet");
        servletRegistrationBean.addInitParameter("name", "javastack");
        servletRegistrationBean.addInitParameter("sex", "man");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean registrationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RegisterFilter());
        registrationBean.addUrlPatterns("/web/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        //servletListenerRegistrationBean.setListener();
        return servletListenerRegistrationBean;
    }

    class RegisterServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String name = getServletConfig().getInitParameter("name");
            String sex = getServletConfig().getInitParameter("sex");
            resp.getOutputStream().println("name is " + name);
            resp.getOutputStream().println("sex is " + sex);
        }
    }

    class RegisterFilter implements Filter {
        RegisterFilter() {
            super();
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        }

        @Override
        public void destroy() {

        }
    }


}