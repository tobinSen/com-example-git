package com.example.spring.mybatis.convert;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomEditorConfigurerConfig {

    @Bean
    public CustomEditorConfigurer customEditorConfigurer(PropertyEditorRegistrar registrar) {
        CustomEditorConfigurer configurer = new CustomEditorConfigurer();
        // 方式一：使用 setPropertyEditorRegistrars 注册
        configurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{registrar});

        //方式二：
        Map<Class<?>, Class<? extends PropertyEditor>> maps = new HashMap<>();
        maps.put(Movie.class, CustomMovieEditor.class);
        configurer.setCustomEditors(maps);

        return configurer;
    }
}
