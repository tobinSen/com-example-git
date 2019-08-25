package com.uplooking.Formal;

import com.google.common.collect.Lists;
import com.uplooking.xml.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MyFormal {


    public static void main(String[] args) {
        UserDTO userDTO1 = new UserDTO(1L, "tongjian1", "123456");
        UserDTO userDTO2 = new UserDTO(2L, "tongjian2", "123456");
        UserDTO userDTO3 = new UserDTO(3L, "tongjian3", "123456");
        UserDTO userDTO4 = new UserDTO(4L, "tongjian4", "123456");
        UserDTO userDTO5 = new UserDTO(5L, "tongjian5", "123456");
        List<UserDTO> list = Lists.newArrayList(userDTO1, userDTO2, userDTO3, userDTO4, userDTO5);
        complete(list); //570
        System.out.println("后：" + list.toString());
    }

    private static void complete(List<UserDTO> list) {

        //形参的生命周期（局部变量，随方法的消失而消失），因为一开始就直接赋能到了局部变量
        list = list.stream().filter(x -> !x.getId().equals(1L)).collect(Collectors.toList());
        for (UserDTO userDTO : list) { //570
            userDTO.setAge(12);
        }
        System.out.println("前：" + list.toString());
    }
}
