package com.uplooking.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "javaServlet", urlPatterns = "/javastack.cn", asyncSupported = true,
        initParams = {
                @WebInitParam(name = "name", value = "javastack"),
                @WebInitParam(name = "sex", value = "man")})
//@ServletComponentScan()
public class JavaServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = getServletConfig().getInitParameter("name");
        String sex = getServletConfig().getInitParameter("sex");
        resp.getOutputStream().println("name is " + name);
        resp.getOutputStream().println("sex is " + sex);
    }
}

/**
 * 动态注册servlet、filter、listener
 */
/*@Component
public class ServletConfig implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        ServletRegistration initServlet = servletContext.addServlet("initServlet", (Servlet) null);
        initServlet.addMapping("/initServlet");
        initServlet.setInitParameter("name", "javastack");
        initServlet.setInitParameter("sex", "man");
    }
}*/
