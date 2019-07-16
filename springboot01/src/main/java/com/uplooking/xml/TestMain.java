package com.uplooking.xml;

import java.util.Date;
import java.util.UUID;

public class TestMain {

    public static void main(String[] args) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setAge(12);
        userDTO.setUsername("张三");
        userDTO.setCreateTime(new Date());
        userDTO.setPassword(UUID.randomUUID().toString());

        String xmlStr = XMLUtil.convertToXml(userDTO);
        System.out.println(xmlStr);

        System.out.println("-------------------xml->obj----------------");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<tong_jian>\n" +
                "    <id>1</id>\n" +
                "    <user_name>张三</user_name>\n" +
                "    <password>16784b54-e334-49b6-8b41-c4b127862f1f</password>\n" +
                "    <age>12</age>\n" +
                "    <create_time>2019-07-16T17:00:24.179+08:00</create_time>\n" +
                "</tong_jian>";

        UserDTO u = (UserDTO)XMLUtil.convertXmlStrToObject(UserDTO.class, xml);

        System.out.println(u.toString());
    }

}
