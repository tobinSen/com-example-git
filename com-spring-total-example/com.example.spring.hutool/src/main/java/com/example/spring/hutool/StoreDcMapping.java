package com.example.spring.hutool;

public class StoreDcMapping {

    private String storeNo;

    private String dcNo;

    private String desc;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "StoreDcMapping{" +
                "storeNo='" + storeNo + '\'' +
                ", dcNo='" + dcNo + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
