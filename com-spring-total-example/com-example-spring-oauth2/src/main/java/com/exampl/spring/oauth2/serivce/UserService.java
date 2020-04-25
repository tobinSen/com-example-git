package com.exampl.spring.oauth2.serivce;

import com.exampl.spring.oauth2.social.qq.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User select(Long id);
}
