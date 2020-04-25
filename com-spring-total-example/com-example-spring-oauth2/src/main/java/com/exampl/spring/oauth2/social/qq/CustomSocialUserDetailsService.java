package com.exampl.spring.oauth2.social.qq;

import com.exampl.spring.oauth2.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 定制 Social UserDetailsService 用于获取系统用户信息
 */
public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        User user = userService.select(Long.valueOf(userId));

        if (user == null) {
            throw new RuntimeException("login.username-or-password.error");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomSocialUserDetails(user.getUsername(), user.getPassword(), userId, user.getNickName(),
                user.getLanguage(), authorities);
    }
}
