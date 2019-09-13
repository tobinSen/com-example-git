package com.example.spring.weixin.pdf;

import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class PDFConverter {

    private FreeMarkerConfigurer freeMarkerConfigurer;

    private XMLWorkerFontProvider fontProvider;

    private String fontFilePath;

    public PDFConverter(String defaultFont) {
        try {
            URL url = ResourceUtils.getURL(defaultFont);
            File fontFile;
            if (ResourceUtils.isJarURL(url)) {
                InputStream inputStream = url.openStream();
                fontFile = new File(defaultFont.substring(defaultFont.lastIndexOf("/") + 1));
                if (!fontFile.exists()) {
                    FileUtils.copyInputStreamToFile(inputStream, fontFile);
                }
            } else {
                fontFile = new File(url.getPath());
            }
            this.fontFilePath = fontFile.getPath();
            this.fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            this.fontProvider.register(this.fontFilePath);
        } catch (Exception e) {
            String error = String.format("字体文件未找到:%s", e);
        }
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public boolean exportToPDF(String pdfName, String templateName, Object data) {
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName + ".ftl");
            String htmlStr = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
            PDFUtils.exportToFile(pdfName, htmlStr, this.fontProvider);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
