package com.example.spring.springsecurity.config;

import com.example.spring.springsecurity.filter.ValidateCodeFilter;
import com.example.spring.springsecurity.sms.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 3，4，2，3，4
 *
 * security的认证方式：
 *  1.默认基本认证，浏览器的portal认证
 *  2.表单认证，网页跳转到一个网页进行表单认证
 *  3.自定义认证过程 -->实现UserDetailsService
 *
 *
 *
 * UsernamePasswordAuthenticationFilter  -->表单认证
 * BasicAuthenticationFilter             -->基本认证的
 * ExceptionTranslationFilter            -->处理认证抛出的异常的
 * FilterSecurityInterceptor             -->进行身份认证的
 *
 * private RequestCache requestCache = new HttpSessionRequestCache();
 * private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 *
 * savedRequest.getRedirectUrl()：在访问那个链接上进行拦截的地址【引发跳转的页面】
 *
 *
 * 处理成功和失败
 * AuthenticationSuccessHandler
 *
 * 登录成功后获取认证信息
 * 方式一：
 *  @GetMapping("index")
 *     public Object index(){
 *         return SecurityContextHolder.getContext().getAuthentication();
 *     }
 * 方式二：
 *  @GetMapping("index")
 *     public Object index(Authentication authentication) {
 *         return authentication;
 *     }
 *
 *
 * AuthenticationFailureHandler
 *
 * oAuth 四种认证模式的类：
 *  AuthorizationCodeTokenGranter -->授权码模式
 *  ImplicitTokenGranter          -->简化模式(比授权码模式少了一个code，直接返回access_token)
 *  ResourceOwnerPasswordTokenGranter -->密码模式(用户|密码->第三方应用->QQ等认证服务器)
 *  ClientCredentialsTokenGranter -->客户端模式(适用于没有前端的命令行应用(应用间认证),这种方式给出的令牌，是针对第三方应用的，而不是针对用户的，即有可能多个用户共享同一个令牌)
 *
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //对方法开启权限控制
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

//    @Autowired
//    private UserDetailService userDetailService;
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(false);
//        return jdbcTokenRepository;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyLogOutSuccessHandler logOutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() //portal基本认证
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)// 添加验证码校验过滤器,这个过滤器必须在认证的的前面
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加短信验证码校验过滤器
        .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler) //权限控制，访问别拒绝的时候
                .and()
                .formLogin() //表单认证方式
                    .loginPage("/authentication/require") //登录跳转 URL（没有认证跳转的地址）
                    .loginProcessingUrl("login")//form表单提交后的url到security来进行处理
                    .successHandler(new MyAuthenticationSucessHandler()) //认证成功的处理器
                    .failureHandler(new MyAuthenticationFailureHandler()) //认证失败的处理器
                .and()
                    .authorizeRequests() //授权所有请求
                    .antMatchers("/authentication/require","login.html","/code/image","/code/sms").permitAll() //login请求不需要认证，否则进入死循环
                    .anyRequest()   //所有请求
                    .authenticated() //都需要认证
                .and()
                    .logout()
                    .logoutUrl("/signout")
                    // .logoutSuccessUrl("/signout/success")
                    .logoutSuccessHandler(logOutSuccessHandler)
                    .deleteCookies("JSESSIONID")
                .and().csrf().disable();

    }
}
