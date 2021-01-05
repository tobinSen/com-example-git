package com.example.spring.mybatis.dao;

import com.example.spring.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record, @Param(value = "id") Long id);

    int updateByPrimaryKey(User record);

    //这个方式我自己加的
    List<User> selectAllUser();

}
