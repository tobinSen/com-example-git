package com.example.spring.poitl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.util.BytePictureUtils;

import java.io.FileOutputStream;
import java.util.Arrays;

public class PoiTlDemo {
    public static void main(String[] args) throws Exception {

        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"), new TextRenderData("FFFFFF", "学历"));

        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");

        //这里的路径要是绝对路径
        XWPFTemplate template = XWPFTemplate.compile("/Users/tongjian/Documents/apache_repository/com-example-git/com-spring-total-example/com.example.spring.poi-tl/src/main/resources/template.docx")
                .render(
//                      new MyDataModel("tom","2019-11-11",new Author("pert"))
//                        new HashMap<String, Object>() {{
//                            put("name", "Sayi");
//                            put("author", new TextRenderData("000000", "Sayi"));
//                            // 超链接
//                            put("link", new HyperLinkTextRenderData("website", "http://deepoove.com"));
//                            // 锚点
//                            put("anchor", new HyperLinkTextRenderData("anchortxt", "anchor:appendix1"));
//                        }}


                        new MyDataTextModel("孙悟空",
                                new TextRenderData("www.baidu.com"),
                                new HyperLinkTextRenderData("google", "www.google.com"),
                                new PictureRenderData(50, 50, ".png", BytePictureUtils.getUrlBufferedImage("http://deepoove.com/images/icecream.png")),
                                new MiniTableRenderData(header, Arrays.asList(row0, row1)))

                );
        FileOutputStream out = new FileOutputStream("output.docx");

        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
