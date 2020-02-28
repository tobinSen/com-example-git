package com.example.spring.hutool.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

public class CaptchaDemo {


    public static void main(String[] args) {
//        //定义图形验证码的长和宽
//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
//
////图形验证码写出，可以写出到文件，也可以写出到流
//        lineCaptcha.write("d:/line.png");
////输出code
//        Console.log(lineCaptcha.getCode());
////验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");
//
////重新生成验证码
//        lineCaptcha.createCode();
//        lineCaptcha.write("d:/line.png");
////新的验证码
//        Console.log(lineCaptcha.getCode());
////验证图形验证码的有效性，返回boolean值
//        lineCaptcha.verify("1234");


        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        System.out.println(lineCaptcha.getCode());
        // 重新生成code
        lineCaptcha.createCode();
        System.out.println(lineCaptcha.getCode());



    }
}
