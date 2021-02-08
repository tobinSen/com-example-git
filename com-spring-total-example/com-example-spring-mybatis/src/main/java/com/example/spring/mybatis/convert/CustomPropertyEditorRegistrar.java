package com.example.spring.mybatis.convert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

    private PropertyEditorSupport propertyEditorSupport;

    public CustomPropertyEditorRegistrar(PropertyEditorSupport propertyEditorSupport) {
        this.propertyEditorSupport = propertyEditorSupport;
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Movie.class, propertyEditorSupport);
    }
}
