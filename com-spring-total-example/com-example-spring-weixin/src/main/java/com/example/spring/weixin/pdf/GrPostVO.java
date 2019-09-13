package com.example.spring.weixin.pdf;

import java.util.List;

public class GrPostVO {

    // 商家采购单编码
    private String poCode;
    // 订货方编码
    private String epCode;
    // 订货方名称
    private String epName;
    // 供应商编码
    private String supplierCode;
    // 供应商名称
    private String supplierName;
    // 收货人签字
    private String receiverSign;
    // 供商签字
    private String supplierSign;
    // 签字日期
    private String signDate;

    private List<GrPostItemVO> items;

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getEpCode() {
        return epCode;
    }

    public void setEpCode(String epCode) {
        this.epCode = epCode;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getReceiverSign() {
        return receiverSign;
    }

    public void setReceiverSign(String receiverSign) {
        this.receiverSign = receiverSign;
    }

    public String getSupplierSign() {
        return supplierSign;
    }

    public void setSupplierSign(String supplierSign) {
        this.supplierSign = supplierSign;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public List<GrPostItemVO> getItems() {
        return items;
    }

    public void setItems(List<GrPostItemVO> items) {
        this.items = items;
    }
}
