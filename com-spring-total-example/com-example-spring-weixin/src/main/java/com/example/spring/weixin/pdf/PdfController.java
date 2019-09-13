package com.example.spring.weixin.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PdfController {

    @Autowired
    protected PDFConverter pdfConverter;

    @RequestMapping("/pdf.do")
    public String pdfPrint() throws Exception {
        try {
            String fileName = "D:\\grPost.pdf";
            File pdfFile = new File(fileName);
            GrPostVO grPostVO = new GrPostVO();
            grPostVO.setEpCode("epCode129312");
            grPostVO.setEpName("百安居上海");
            grPostVO.setPoCode("0012475832");
            grPostVO.setSignDate("2019-09-02 17:12:21");
            grPostVO.setSupplierCode("spCode239121");
            grPostVO.setSupplierName("百安居上海");
            List<GrPostItemVO> grPostItemVOList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                GrPostItemVO grPostItemVO = new GrPostItemVO();
                grPostItemVO.setItemCode("1");
                grPostItemVO.setBasicUnitCode("unitCode");
                grPostItemVO.setBasicUnitNum(BigDecimal.TEN);
                grPostItemVO.setProductCode("1273213");
                grPostItemVO.setProductName("百安居" + i);
                grPostItemVOList.add(grPostItemVO);
            }
            grPostVO.setItems(grPostItemVOList);
            pdfConverter.exportToPDF(fileName, "gr_post", grPostVO);
            return "打印成功";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "打印失败";
        }
    }
}
