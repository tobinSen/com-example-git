package com.example.spring.shiro.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.List;

public class YamlDemo {

    public static void main(String[] args) {
        //指定yaml文件的root对象解析成Person类型
        Yaml yaml = new Yaml(new Constructor(Person.class));
        Person ret = yaml.loadAs(YamlDemo.class.getClassLoader().getResourceAsStream("test.yml"),Person.class);
        System.out.println(ret);
    }


    public static class Person {
        private String given;
        private String family;
        private List<Address> address;

        public String getGiven() {
            return given;
        }

        public void setGiven(String given) {
            this.given = given;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public List<Address> getAddress() {
            return address;
        }

        public void setAddress(List<Address> address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "given='" + given + '\'' +
                    ", family='" + family + '\'' +
                    ", address=" + address +
                    '}';
        }
    }

    public static class Address {
        private String lines;
        private String city;
        private String state;
        private Integer postal;

        public String getLines() {
            return lines;
        }

        public void setLines(String lines) {
            this.lines = lines;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Integer getPostal() {
            return postal;
        }

        public void setPostal(Integer postal) {
            this.postal = postal;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "lines='" + lines + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", postal=" + postal +
                    '}';
        }
    }
}
