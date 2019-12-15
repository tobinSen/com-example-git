package com.example.spring.springsecurity.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {
    //1.security的请求缓存对象
    private RequestCache requestCache = new HttpSessionRequestCache();
    //2.重定向对象
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.保存请求对象
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            //2.从哪个url开始发起请求的，指的是发起请求的URL
            String targetUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
                redirectStrategy.sendRedirect(request, response, "/login.html");
        }
        return "访问的资源需要身份认证！";
    }

    //AuthenticationSuccessHandler 请求成功后返回的时候可以携带认证成功的信息
//    @GetMapping("index")
//    public Object index(){
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//    @GetMapping("index")
//    public Object index(Authentication authentication) {
//        return authentication;
//    }
}
