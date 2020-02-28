package com.example.spring.hutool.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtilDemo {

    public static void main(String[] args) {
        QrConfig config = new QrConfig();
        // 高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        config.setImg("/Users/tongjian/qrcodeCustom.jpg");
        QrCodeUtil.generate(IdUtil.simpleUUID(), config, FileUtil.file("/Users/tongjian/qrcodeCustom.jpg"));

    }
}
