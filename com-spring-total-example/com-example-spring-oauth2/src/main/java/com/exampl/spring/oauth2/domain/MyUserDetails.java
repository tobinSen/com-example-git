package com.exampl.spring.oauth2.domain;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class MyUserDetails extends User {
    private com.exampl.spring.oauth2.domain.User user;

    public MyUserDetails(com.exampl.spring.oauth2.domain.User user) {
        super(user.getUserName(), user.getPassword(), true, true, true, true, Collections.emptySet());
        this.user = user;
    }
}
