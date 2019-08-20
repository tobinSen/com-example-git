package com.example.spring.freemark.code;

import org.apache.commons.lang3.StringUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取条形码
 * <p>
 * <p>
 * 创建日期：2016年7月22日 下午1:13:11
 * 操作用户：zhoubang
 */
@Controller
@RequestMapping("/barCode")
public class BarCodeController {

    @RequestMapping(value = "/getBarCodeByCode")
    public String getBarCodeByCode(String code, HttpServletResponse response, boolean noText) throws Exception {
        try {
            if (!StringUtils.isEmpty(code)) {
                JBarcode jBarcode = getJbarcode();
                jBarcode.setShowText(!noText);
                ImageIO.write(jBarcode.createBarcode(StringUtils.upperCase(code)), "jpeg", response.getOutputStream());
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JBarcode getJbarcode() {
        JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
        localJBarcode.setEncoder(Code39Encoder.getInstance());
        localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
        localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
        localJBarcode.setShowCheckDigit(false);
        localJBarcode.setCheckDigit(false);
        return localJBarcode;
    }
}
