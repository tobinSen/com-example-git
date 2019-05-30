package com.uplooking.factories;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class LoginFactory {

    private Map<Integer, LoginFactory> map = Maps.newHashMap();

    public LoginFactory(ObjectProvider<LoginFactory[]> objectProvider) {
        LoginFactory[] loginFactories = objectProvider.getIfAvailable();
        Arrays.stream(loginFactories).collect(Collectors.toMap(LoginFactory::getAuthType, Function.identity()));

    }

    protected abstract int getAuthType();

    protected abstract void login(String userName, String passWord);
}
