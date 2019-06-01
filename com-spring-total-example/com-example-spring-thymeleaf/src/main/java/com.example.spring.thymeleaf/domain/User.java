package com.example.spring.thymeleaf.domain;

import javax.validation.constraints.NotEmpty;

public class User {

    @NotEmpty(message="账户不能为空")
    private String uname;
    @NotEmpty(message="密码不能为空")
    private String upwd;
    private String apwd;
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUpwd() {
        return upwd;
    }
    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
    public String getApwd() {
        return apwd;
    }
    public void setApwd(String apwd) {
        this.apwd = apwd;
    }
    public User() {
        super();
    }
    public User(String uname, String upwd) {
        super();
        this.uname = uname;
        this.upwd = upwd;
    }
    @Override
    public String toString() {
        return "User [uname=" + uname + ", upwd=" + upwd + ", apwd=" + apwd + "]";
    }
}
