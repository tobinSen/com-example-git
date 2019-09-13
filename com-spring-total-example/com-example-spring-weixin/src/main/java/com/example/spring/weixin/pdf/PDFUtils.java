package com.example.spring.weixin.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PDFUtils {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static boolean exportToFile(String fileName, String htmlData, String fontPath) {
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register(fontPath);
        return exportToFile(fileName, htmlData, fontProvider);
    }

    public static boolean exportToFile(String fileName, String htmlData, XMLWorkerFontProvider fontProvider) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            Document document = new Document(PageSize.A4);
            convertToPDF(PdfWriter.getInstance(document, outputStream), fontProvider, document, htmlData);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("PDF导出到文件失败", e);
        }
    }

    private static void convertToPDF(PdfWriter writer, XMLWorkerFontProvider fontProvider, Document document, String htmlString) {
        document.open();
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(htmlString.getBytes()), UTF_8, fontProvider);
        } catch (IOException e) {
            throw new RuntimeException("PDF文件生成异常", e);
        } finally {
            document.close();
        }
    }

}
