package com.example.spring.jwt.demo;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Demo {


    private String secret = "a1g2y47dg3dj59fjhhsd7cnewy73j";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void test1() throws InterruptedException {// 生成JWT
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", "zss");
        claims.put("age", 18);

        //生成token
        String token = Jwts.builder()
                .setClaims(claims)
                .setId("666")  //登录用户的id
                .setSubject("小马")  //登录用户的名称
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 1000))//过期时间
                .setIssuedAt(new Date(System.currentTimeMillis()))//当前时间
                .signWith(SignatureAlgorithm.HS512, this.secret)//头部信息 第一个参数为加密方式为哈希512  第二个参数为加的盐为secret字符串
                .compact();

        System.out.println("token令牌是：" + token);

        Claims claims1 = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();

        Date d1 = claims1.getIssuedAt();
        Date d2 = claims1.getExpiration();
        System.out.println("username参数值：" + claims1.get("username"));
        System.out.println("登录用户的id：" + claims1.getId());
        System.out.println("登录用户的名称：" + claims1.getSubject());
        System.out.println("令牌签发时间：" + sdf.format(d1));
        System.out.println("令牌过期时间：" + sdf.format(d2));

        Thread.sleep(10_000);


        // jwt 过期会直接抛异常
        try {
            Claims claims2 = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();

            Date d3 = claims2.getIssuedAt();
            Date d4 = claims2.getExpiration();
            System.out.println("username参数值：" + claims2.get("username"));
            System.out.println("登录用户的id：" + claims2.getId());
            System.out.println("登录用户的名称：" + claims2.getSubject());
            System.out.println("令牌签发时间：" + sdf.format(d3));
            System.out.println("令牌过期时间：" + sdf.format(d4));
        } catch (ExpiredJwtException e) {
            System.out.println("令牌过期了");
            e.printStackTrace();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
}
