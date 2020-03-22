package com.example.spring.hutool.spring;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;

import java.lang.annotation.Annotation;

public class MyAbstractTypeHierarchyTraversingFilter extends AbstractTypeHierarchyTraversingFilter {

    private Class<? extends Annotation> annotationType;

    protected MyAbstractTypeHierarchyTraversingFilter(Class<? extends Annotation> annotationType, boolean considerInherited, boolean considerInterfaces) {
        super(considerInherited, considerInterfaces);
        this.annotationType = annotationType;
    }

    @Override
    protected boolean matchSelf(MetadataReader metadataReader) {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        return annotationMetadata.hasAnnotatedMethods(annotationMetadata.getClassName());
    }
}
