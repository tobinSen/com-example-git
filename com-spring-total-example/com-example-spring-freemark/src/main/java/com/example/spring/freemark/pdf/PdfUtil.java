package com.example.spring.freemark.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.Map;

public class PdfUtil {

    public static byte[] generatePdf(String templeteBuffer, String templateName, Map<String, Object> map, String basePath, String fontFile) throws Exception, TemplateException, ParserConfigurationException, DocumentException, SAXException {

        /** ----freemarker模板解析---- **/
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setDefaultEncoding("utf-8");
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(templateName, templeteBuffer);

        configuration.setTemplateLoader(stringLoader);
        Template template = configuration.getTemplate(templateName);
        StringWriter str = new StringWriter();
        template.process(template, str);

        /** -------生成PDF------- **/
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(str.toString().getBytes()));

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        String simusun = fontFile;
        if (simusun != null) {
            fontResolver.addFont(simusun, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.getSharedContext().setFontResolver(fontResolver);
        }
        // 解决图片的相对路径问题
        renderer.getSharedContext().setBaseURL("file:" + basePath);
        renderer.layout();

        //返回PDF字节数组 一边上传文件服务器
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024000);
        renderer.createPDF(os);

        // 输出PDF文件
        // OutputStream fileStream = new FileOutputStream("d:\\test.pdf");
        // fileStream.write(os.toByteArray());
        // fileStream.close();

        return os.toByteArray();
    }
}
