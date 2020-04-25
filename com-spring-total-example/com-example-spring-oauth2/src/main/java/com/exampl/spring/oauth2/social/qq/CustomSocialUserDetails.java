package com.exampl.spring.oauth2.social.qq;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;

/**
 * 定制 SocialUserDetails 封装 Social 登录用户信息
 */
public class CustomSocialUserDetails extends User implements SocialUserDetails {

    private String userId;

    private String nickname;

    private String language;

    public CustomSocialUserDetails(String username, String password, String userId, String nickname, String language,
                                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickname = nickname;
        this.language = language;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLanguage() {
        return language;
    }
}
