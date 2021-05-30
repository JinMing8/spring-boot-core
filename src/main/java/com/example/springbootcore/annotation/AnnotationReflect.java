package com.example.springbootcore.annotation;

import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@TransactionService(name = "hehe")
public class AnnotationReflect {

//    public static void main(String[] args) {
//        // 直接调用注解的方法获得value
//        AnnotatedElement annotatedElement = AnnotationReflect.class;
//        TransactionService annotation = annotatedElement.getAnnotation(TransactionService.class);
//        String name = annotation.name();
//        System.out.println( "TransactionService name : " + name);
//    }

    public static void main(String[] args) {
        AnnotatedElement annotatedElement = AnnotationReflect.class;
        Annotation annotation = annotatedElement.getAnnotation(TransactionService.class);
        Set<Annotation> allAnnotations = AnnotationReflect.getAllAnnotations(annotation);
        allAnnotations.stream().peek(a -> System.out.println(a.annotationType().getSimpleName())).forEach(AnnotationReflect::printAnnotationAttribute);
    }

    /**
     * 获取一个注解上的其他注解
     * @param annotation
     * @return
     */
    public static Set<Annotation> getAllAnnotations(Annotation annotation) {
        if(ObjectUtils.isEmpty(annotation)) {
            return Collections.emptySet();
        }
        // 获取annotation上标记的所有annotation
        Annotation[] annotations = annotation.annotationType().getAnnotations();
        if(ObjectUtils.isEmpty(annotations)) {
            return Collections.emptySet();
        }
        // 查找注解上的其他注解集合
        Set<Annotation>  metaAnnotation = Arrays.stream(annotations)
                // 排除java标准注解@Target,@Document等,它们因相互依赖,会导致不断的递归
                .filter(anno -> !Target.class.getPackage().equals(anno.annotationType().getPackage()))
                .collect(Collectors.toSet());
        // 递归查找注解的元注解集合
        Set<Annotation> metaMetaAnnotation = metaAnnotation.stream().map(AnnotationReflect::getAllAnnotations)
                .collect(HashSet::new, Set::addAll, Set::addAll);
        metaAnnotation.addAll(metaMetaAnnotation);
        return metaAnnotation;
    }

    public static void printAnnotationAttribute(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        // ReflectionUtils是Springframework的工具类
        ReflectionUtils.doWithMethods(annotationType, method -> {
            System.out.printf("@%s.%s = %s\n", annotationType.getSimpleName(), method.getName(), ReflectionUtils.invokeMethod(method, annotation));
            // 过滤出无参的方法和非Annotation接口的方法
        }, method -> method.getParameterCount() == 0 && !method.getDeclaringClass().equals(Annotation.class));
    }
}
