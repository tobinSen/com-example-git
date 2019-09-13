package com.example.spring.weixin.pdf;

import java.math.BigDecimal;

public class GrPostItemVO {

    // 行项目编号
    private String itemCode;
    // 商品编码
    private String productCode;
    // 商品描述
    private String productName;
    // 商家采购单数
    private BigDecimal planNum;
    // 单位编码
    private String unitCode;
    // 实收数
    private BigDecimal packageUnitNum;
    // 包装单位编码
    private String packageUnitCode;
    // 实收数量
    private BigDecimal basicUnitNum;

    private String basicUnitCode;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPlanNum() {
        return planNum;
    }

    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public BigDecimal getPackageUnitNum() {
        return packageUnitNum;
    }

    public void setPackageUnitNum(BigDecimal packageUnitNum) {
        this.packageUnitNum = packageUnitNum;
    }

    public String getPackageUnitCode() {
        return packageUnitCode;
    }

    public void setPackageUnitCode(String packageUnitCode) {
        this.packageUnitCode = packageUnitCode;
    }

    public BigDecimal getBasicUnitNum() {
        return basicUnitNum;
    }

    public void setBasicUnitNum(BigDecimal basicUnitNum) {
        this.basicUnitNum = basicUnitNum;
    }

    public String getBasicUnitCode() {
        return basicUnitCode;
    }

    public void setBasicUnitCode(String basicUnitCode) {
        this.basicUnitCode = basicUnitCode;
    }
}
