package com.example.spring.weixin;

import com.example.spring.weixin.pdf.PDFConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
@Configuration
public class WeixinApplication {

    @Autowired
    private PDFConverter pdfConverter;

    public static void main(String[] args) {
        SpringApplication.run(WeixinApplication.class, args);
    }

    @Bean
    public PDFConverter pdfConverter(FreeMarkerConfigurer freeMarkerConfigurer) {
        PDFConverter pdfConverter = new PDFConverter(ResourceUtils.CLASSPATH_URL_PREFIX + "fonts/simsun.ttf");
        pdfConverter.setFreeMarkerConfigurer(freeMarkerConfigurer);

        return pdfConverter;

    }
}
