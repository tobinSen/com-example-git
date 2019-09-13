<#include "public/header.ftl">
<@header>
<style type="text/css">
    * {
        margin: 0;
        padding: 0;
    }

    .container {
        width: 100%;
        margin: 0 auto;
    }

    .title {
        height: 14px;
        text-align: center;
        font-size: 14px;
        font-weight: bold;
    }

    .manifest-header table > tbody > tr > td {
        font-size: 12px;
        font-weight: normal;
        text-align: left;
        padding: 6px 10px;
        border-right: 1px solid transparent;
        border-bottom: 1px solid transparent;
    }

    .manifest-header table {
        width: 100%;
        border-top: 1px solid transparent;
        border-left: 1px solid transparent;
        border-spacing: 0 20px;
    }

    .manifest-body table > thead > tr > th,
    .manifest-body table > tbody > tr > td {
        font-size: 12px;
        font-weight: normal;
        text-align: center;
        padding: 6px 10px;
        border-right: 1px solid #000;
        border-bottom: 1px solid #000;
    }

    .manifest-body table {
        width: 100%;
        border-top: 1px solid #000;
        border-left: 1px solid #000;
    }

    .manifest-footer table > tbody > tr > td {
        font-size: 12px;
        font-weight: normal;
        text-align: left;
        padding: 6px 10px;
        border-right: 1px solid transparent;
        border-bottom: 1px solid transparent;
    }

    .manifest-footer table > tbody > tr > td > img {
        width: 200px;
    }

    .manifest-footer table {
        width: 100%;
        border-top: 1px solid transparent;
        border-left: 1px solid transparent;
        border-spacing: 0 10px;
    }
</style>

<div class="container">
    <h1 class="title">收货清单</h1>
    <div class="manifest-header">
        <table width="100%" cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td width="50%">商家采购订单号：${poCode!''}</td>
                <td width="50%">订货方：${epCode!''}-${epName!''}</td>
            </tr>
            <tr>
                <td width="50%">供应商编码：${supplierCode!''}</td>
                <td width="50%">供应商名称：${supplierName!''}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="manifest-body">
        <table width="100%" cellspacing="0" cellpadding="2">
            <thead>
            <tr>
                <th width="10%">行项目编号</th>
                <th width="10%">商品编码</th>
                <th width="30%">商品描述</th>
                <th width="15%">商家采购单数</th>
                <th width="5%">单位</th>
                <th width="10%">实收数</th>
                <th width="5%">单位</th>
                <th width="10%">实收数量</th>
                <th width="5%">单位</th>
            </tr>
            </thead>
            <tbody>
            <#if items??>
            <#list items as item>
            <tr>
                <td>${item.itemCode!''}</td>
                <td>${item.productCode!''}</td>
                <td>${item.productName!''}</td>
                <td>${item.planNum!''}</td>
                <td>${item.unitCode!''}</td>
                <td>${item.packageUnitNum!''}</td>
                <td>${item.packageUnitCode!''}</td>
                <td>${item.basicUnitNum!''}</td>
                <td>${item.basicUnitCode!''}</td>
            </tr>
            </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <br/>
    <div class="manifest-footer">
        <table width="100%" cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td width="50%">收货人：<img src="${receiverSign!''}"/></td>
                <td width="50%">供商（送货人）签字：<img src="${supplierSign!''}"/></td>
            </tr>
            <tr>
                <td width="50%">签字日期：${signDate!''}</td>
                <td width="50%">供商（送货人）签字日期：${signDate!''}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</@header>