package com.example.spring.ladp.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import java.util.Date;

@Data
@ToString
@Entry(objectClasses = {"bicPersonExt", "bicPerson"}, base = "ou=person,dc=coreservice")
public class Person {

    /**
     * 主键
     */
    @Attribute
    private String personId;

    /**
     * 人员姓名
     */
    @Attribute(name = "cn")
    private String personName;
    /**
     * 组织ID
     */
    @Attribute(name = "orgId")
    private String orgId;
    /**
     * 性别
     */
    @Attribute(name = "sex")
    private Integer sex;
    /**
     * 电话
     */
    @Attribute(name = "mobile")
    private String mobile;
    /**
     * 邮箱
     */
    @Attribute(name = "email")
    private String email;
    /**
     * 工号
     */
    @Attribute(name = "jobNo")
    private String jobNo;
    /**
     * 学号
     */
    @Attribute(name = "studentId")
    private String studentId;

    /**
     * 证件类型
     */
    @Attribute(name = "certType")
    private Integer certType;
    /**
     * 证件号码
     */
    @Attribute(name = "certificateNo")
    private String certNo;

    @Attribute
    protected Date createTime;

    /**
     * 更新时间
     */
    @Attribute
    protected Date updateTime;
    /**
     * 状态
     */
    @Attribute
    protected Integer status;

    @Attribute
    protected Integer disOrder;

    /**
     * 工作单位
     */
    @Attribute
    private String company;
}