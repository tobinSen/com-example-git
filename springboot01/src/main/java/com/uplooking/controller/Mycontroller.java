package com.uplooking.controller;

import com.uplooking.pojo.Dog;
import com.uplooking.pojo.Person;
import org.apache.catalina.SessionListener;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

@HandlesTypes(value = {Person.class, Dog.class})
public class Mycontroller implements ServletContainerInitializer {

    //提供ServletContainerInitializer的实现类必须绑定在
    //META-INF/services/javax.servlet.ServletContainerInitializer文件的内容为
    //该类实现类的全类名

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //set为person和dog类
        //ServletContext ==>applicationContext一个项目一个域对象
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("userServlet", Servlet.class);
        userServlet.addMapping("/*");

        FilterRegistration.Dynamic filter = servletContext.addFilter("MyFilter", Filter.class);

        filter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true, "/user");
        servletContext.addListener(SessionListener.class);

    }
}
