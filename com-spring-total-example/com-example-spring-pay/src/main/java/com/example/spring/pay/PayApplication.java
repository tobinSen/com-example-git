package com.example.spring.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 公钥 <--> 私钥
 * <p>
 * client （公钥加密 + 文件）--->  server (私钥解密)
 * <p>
 * client （公钥解密）     <---文件->摘要（私钥加密）-->数字签名
 * <p>
 * client （浏览器中证书中心进行匹配  <---  文件->摘要（私钥加密）-->数字签名 + （数字证书中心用私钥对公钥做加密）->数字证书
 * <p>
 * <p>
 * 问题1：
 * client 使用的不是server提供的公钥，client并不知道公钥是否是server端发送的
 */

@SpringBootApplication(scanBasePackages = "com.example.spring.pay")
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }


}
