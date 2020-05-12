package com.example.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@Controller
public class WebApplication {

    @Autowired
    private AbstractXlsViewConfig xlsViewConfig;
    @Autowired
    private AbstractPdfViewConfig pdfViewConfig;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RequestMapping("xls.do")
    public ModelAndView xls(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return new ModelAndView(xlsViewConfig);
    }

    @RequestMapping("pdf.do")
    public ModelAndView pdf(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return new ModelAndView(pdfViewConfig);
    }
}
