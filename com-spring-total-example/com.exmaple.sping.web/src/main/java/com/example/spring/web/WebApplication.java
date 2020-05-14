package com.example.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * @see <a href="https://blog.csdn.net/cold___play/article/details/102839502">Convert类型转换器</a>
     */
    @RequestMapping("pdf.do")
    public ModelAndView pdf(@RequestParam("ids") List<Long> ids, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(ids);

        return new ModelAndView(pdfViewConfig);
    }


}
