package com.uplooking.importselector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    /**
     * annotationMetadata:当前标注@Import注解类的所有注解信息
     *
     * @return ： 就是要导入到容器中的组件全类名
     */

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //不能返回null
        return new String[]{"com.uplooking.controller"};
    }
}
