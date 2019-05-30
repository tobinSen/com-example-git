package com.uplooking.factories;

import org.springframework.beans.factory.ObjectProvider;

public class PCLoginFactory extends LoginFactory {

    public PCLoginFactory(ObjectProvider<LoginFactory[]> objectProvider) {
        super(objectProvider);
    }

    @Override
    protected int getAuthType() {
        return 0;
    }

    @Override
    protected void login(String userName, String passWord) {
        //这是具体的登录逻辑
    }
}
