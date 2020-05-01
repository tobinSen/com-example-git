package com.exampl.spring.oauth2.serivce.impl;

import com.exampl.spring.oauth2.domain.MyUserDetails;
import com.exampl.spring.oauth2.domain.User;
import com.exampl.spring.oauth2.serivce.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Set<User> users = new HashSet<>();

    static {
        users.add(new User(1, "admin", new BCryptPasswordEncoder().encode("123456")));
    }

    //验证用户方法
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = users.stream()
                .filter((u) -> u.getUserName().equals(s))
                .findFirst();
        if (!user.isPresent())
            throw new UsernameNotFoundException("用户不能发现!");
        else
            return UserDetailConverter.convert(user.get());
    }

    @Override
    public com.exampl.spring.oauth2.social.qq.User select(Long id) {
        return null;
    }

    private static class UserDetailConverter {
        static UserDetails convert(User user) {
            return new MyUserDetails(user);
        }
    }
}
