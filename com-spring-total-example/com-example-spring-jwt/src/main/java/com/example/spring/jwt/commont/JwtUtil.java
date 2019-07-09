package com.example.spring.jwt.commont;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt: header + payload + signature--->token
 */
@Configuration
public class JwtUtil {

    @Value("${jwt.expire_time}")
    private long expirationTime;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 生产jwtToken
     *
     * @param username
     * @return
     */
    public String generateToken(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        String jwtToken = Jwts.builder()
                .setClaims(map) //设置负载payload
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))//设置过期时间
                .signWith(SignatureAlgorithm.HS256, secret) // 根据密匙进行加密方式
                .compact();
        return jwtToken;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public String validateToken(String token) {
        if (null != token) {
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(secret) //设置密匙
                    .parseClaimsJws(token) //token
                    .getBody();
            String username = body.get("username").toString();
            if (null == username || username.isEmpty()) {
                throw new RuntimeException("wrong token with username");
            } else {
                return username;
            }
        } else {
            throw new RuntimeException("token is null");
        }
    }
}
