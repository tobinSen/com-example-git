package com.example.spring.springsecurity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * SecurityConfig
 * UsernamePasswordAuthenticationFilter -> 登录验证 + 返回token
 * <p>
 * BasicAuthenticationFilter -> 请求拦截验证token的有效性
 * <p>
 * // 如果请求头中有token，则进行解析，并且设置授权信息
 * SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
 * <p>
 * JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
 */
public class JwtTokenUtils {

    /**
     * 角色的key
     **/
    public static final String ROLE_CLAIMS = "rol";
    /**
     * rememberMe 为 false 的时候过期时间是1个小时
     */
    public static final long EXPIRATION = 60L * 60L;
    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    // JWT token defaults

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);

    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;

        String tokenPrefix = Jwts.builder()
                //设置头部
                .setHeaderParam("typ", TOKEN_TYPE)
                //设置加密方式
                .signWith(secretKey, SignatureAlgorithm.HS512)
                //设置数据信息
                .claim(ROLE_CLAIMS, String.join(",", roles))
                .setIssuer("SnailClimb")
                .setIssuedAt(new Date())
                //设置用户名
                .setSubject(username) //用户名
                //设置有效期
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))//有效期
                //进行加密计算，生成token
                .compact();
        return TOKEN_PREFIX + tokenPrefix;
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户所有角色
     */
    public static List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token)
                .get(ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token) //解析带有数据的token
                .getBody();
    }
}
