package com.example.springbootcore.annotation;

import com.sun.tools.doclets.standard.Standard;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@TransactionService
public class AnnotationMetadataGetAttributes {

    public static void main(String[] args) {
        AnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(AnnotationMetadataGetAttributes.class);
        LinkedHashSet<String> annotationMetadataSet = annotationMetadata.getAnnotationTypes()
                .stream().peek(System.out::println)
                .map(a -> new StandardAnnotationMetadata(a.getClass()))
                .map(StandardAnnotationMetadata::getAnnotationTypes)
                .peek(System.out::println)
                .collect(LinkedHashSet::new, Set::addAll, Set::addAll);


        annotationMetadataSet.forEach(anno -> {
            Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(anno);
            if(attributes!=null) {
                attributes.forEach( (k,v) -> System.out.printf("注解 @%s  key=%s , value= %s\n",anno,k,v));
            }
        });

    }
}
