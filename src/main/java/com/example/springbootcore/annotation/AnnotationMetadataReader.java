package com.example.springbootcore.annotation;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class AnnotationMetadataReader {

    public static void main(String[] args) throws IOException {
        String className = AnnotationMetadataReader.class.getName();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(className);
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        annotationMetadata.getAnnotationTypes().forEach(t -> {
            Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes(t);
            metaAnnotationTypes.forEach(System.out::println);
        });
    }
}
