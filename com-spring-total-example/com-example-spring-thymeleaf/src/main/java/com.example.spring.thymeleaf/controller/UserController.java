package com.example.spring.thymeleaf.controller;

import com.example.spring.thymeleaf.domain.Book;
import com.example.spring.thymeleaf.domain.User;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "/test/hello", method = RequestMethod.GET)
    public String method1(Model model) {
        //thymeleaf 渲染规则  前缀 /templates/ 后缀 .html
        //model.addAttribute("title", "标题");
        model.addAttribute("login", "请登录");
        return "";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String method2(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String method3(@Valid @ModelAttribute(value = "user") User user,
                          Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "login";
        }
        System.out.println(user);
        if (user.getUpwd().equals("a123")) {
            request.getSession().setAttribute("user", user);
        } else {
            request.setAttribute("result", "FAIL");
        }
        return "login";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String method4(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/book/list", method = RequestMethod.GET)
    public String method5(Model model) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(new Book("书名" + i, "作者" + i));
        }
        model.addAttribute("books", books);
        return "list";
    }
}
