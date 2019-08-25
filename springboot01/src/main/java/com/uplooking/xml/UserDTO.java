package com.uplooking.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

//@XmlType(propOrder = {"createTime", "age"})
@XmlAccessorType(XmlAccessType.FIELD)//序列化字段
@XmlRootElement(name = "tong_jian")
public class UserDTO implements Serializable {


    @XmlElement(name = "id")
    private Long id;
    @XmlElement(name = "user_name")
    private String username;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name = "age")
    private Integer age;

    //类型适配器
    @XmlJavaTypeAdapter(value = DateXmlAdapter.class)
    @XmlElement(name = "create_time")
    private Date createTime;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }
}
