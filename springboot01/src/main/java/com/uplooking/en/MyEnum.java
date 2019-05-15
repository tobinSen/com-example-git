package com.uplooking.en;

import com.alibaba.fastjson.JSONObject;

public enum MyEnum {

    Spring(1, "spring", "春季") {
        @Override
        public void getDesc() {

        }
    },
    Summer(2, "summer", "夏季") {
        @Override
        public void getDesc() {

        }
    },
    Automer(3, "automer", "秋季") {
        @Override
        public void getDesc() {

        }
    },
    Winter(4, "winter", "冬季") {
        @Override
        public void getDesc() {

        }
    };


    private Integer index;
    private String code;
    private String name;

    MyEnum(Integer index, String code, String name) {
        this.index = index;
        this.code = code;
        this.name = name;
    }

    public boolean equals(Integer index) {
        return this.index == index;
    }

    public boolean equals(String code) {
        return this.code == code;
    }

    public Integer getIndex() {
        return index;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public abstract void getDesc();

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("index", index);
        jsonObject.put("code", code);
        jsonObject.put("name", name);
        return jsonObject;
    }

    public static JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spring", Spring.toJSONObject());
        jsonObject.put("summer", Summer.toJSONObject());
        jsonObject.put("automer", Automer.toJSONObject());
        jsonObject.put("winter", Winter.toJSONObject());
        return jsonObject;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = MyEnum.getJSONObject();
        System.out.println(jsonObject);
    }
}
