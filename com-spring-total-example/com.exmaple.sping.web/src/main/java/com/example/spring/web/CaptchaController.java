package com.example.spring.web;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 需要开启  <mvc:annotation-driven enable-matrix-variables="true"/>
     * 请求；/test/123;q=123/h/456;q=456
     *
     * @param q1
     * @param q2
     */
    @RequestMapping(path = "/test/{ownerId}/h/{petId}") // 路径段中
    public void findPet(@MatrixVariable(name = "q", pathVar = "ownerId") int q1,
                        @MatrixVariable(name = "q", pathVar = "petId") int q2) {
        System.out.println(q1 + "---" + q2);
    }

    /**
     * color=red,green,blue
     * 或者可以使用重复的变量名：
     * color=red;color=green;color=blue
     * <p>
     * 更复杂的示例
     * 请求：/test2/123;q=123;r=222;m=4/h/456;q=456;p=234
     * 结果：m1   {"q":["123","456"],"r":["222"],"m":["4"],"p":["234"]}
     * m2   {"q":["456"],"p":["234"]}
     * <p>
     * 需要使用阿里巴巴的fastjson
     * <dependency>
     * <groupId>com.alibaba</groupId>
     * <artifactId>fastjson</artifactId>
     * <version>1.2.3</version>
     * </dependency>
     *
     * @param m1
     * @param m2
     */
    @RequestMapping(path = "/test2/{ownerId}/h/{petId}")
    public void findPet2(@MatrixVariable Map<String, String> m1,
                         @MatrixVariable(pathVar = "petId") Map<String, String> m2) {
        System.out.println(JSON.toJSONString(m1));
        System.out.println(JSON.toJSONString(m2));

    }

    // http://127.0.0.1:9082/sys/v1/count/test/id35;price=100;type=1,3/msg/msg44
    // http://127.0.0.1:9082/sys/v1/count/test/id35;price=100;type=1,3/msg/msg44;vol=100,45
    @GetMapping("test/{id}/msg/{tip}")
    public String testMatrix(@MatrixVariable(pathVar = "id") Map<String, List<String>> map,
                             @MatrixVariable(value = "vol", pathVar = "tip") List<String> list,
                             @PathVariable("id") String id, @PathVariable("tip") String tip,
                             @MatrixVariable("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob) {
        System.out.println("id:" + id);
        System.out.println("tip:" + tip);
        System.out.println("map:" + map);
        System.out.println("list:" + list);
        return "";
    }

    //http://127.0.0.1:9082/sys/v1/count/tablet;low=100;hight=1000?manufacturer=google
    @RequestMapping("/{category}")
    public String filterProducts(
            @PathVariable("category") String category,
            @MatrixVariable Map<String, List<String>> filterParams,
            @RequestParam("manufacturer") String manufacturer,
            @MatrixVariable("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob) {
        System.out.println("category:" + category);
        System.out.println("filterParams:" + filterParams);
        System.out.println("manufacturer:" + manufacturer);
        System.out.println("model:" + dob);
        return "";
    }


}
