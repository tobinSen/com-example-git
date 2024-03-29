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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SpringBootApplication
@Controller
public class WebApplication {

    @Autowired
    private AbstractXlsViewConfig xlsViewConfig;
    @Autowired
    private AbstractPdfViewConfig pdfViewConfig;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
//        System.out.println(isNodeReachable("192.168.6.12"));
//        System.out.println(isNodeReachable("127.0.0.1"));
//        System.out.println(isNodeReachable("localhost"));
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

        Map<String, String> map = new HashMap<>();
        map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s, s2) -> s, TreeMap::new));

        return new ModelAndView(pdfViewConfig);
    }

    public static boolean isNodeReachable(String hostname) {
        try {
            return 0 == Runtime.getRuntime().exec("ping -c 1 " + hostname).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }




}
