package com.example.spring.shiro.configuration;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditorSupport;

//配置属性自定义转换
public class CustomMovieEditor extends PropertyEditorSupport implements PropertyEditorRegistrar {


    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] data = text.split("-");

        setValue(data);
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(this.getClass(), this);
    }
}
