package com.example.spring.web;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Autowired
    private Producer captchaProducer;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public Map<String, Object> getCode(HttpServletResponse response) throws IOException {

        try {
            //CaptchaUtils.generate(response, CaptchaUtils.generateCode());
            response.addCookie(new Cookie("name", "tongjian"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * java.lang.IllegalStateException: Cannot call sendError() after the response has been committed
         *
         * 1、直接先流返回了，后面的return是不能返回的 【这里会产生两个流，存在问题】
         * 2、response.addCookie + return 返回的时候是合并一起返回的是【同一个流】
         */
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("img", CaptchaUtils.generate(CaptchaUtils.generateCode()));
        return ajax;
    }
}
