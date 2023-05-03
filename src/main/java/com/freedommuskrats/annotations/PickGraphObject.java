package com.freedommuskrats.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

//https://github.com/spring-projects/spring-graphql/tree/main/spring-graphql/src/main/java/org/springframework/graphql/data/method
//https://github.com/spring-projects/spring-graphql/blob/a66b49540bc79b1eea08333b02e5a48621a0881a/spring-graphql/src/test/java/org/springframework/graphql/data/method/annotation/support/SchemaMappingDetectionTests.java
// AnnotatedControllerConfigurer in spring-graphql
// SchemaMappingBeanFactoryInitializationAotProcessor in spring-graphql
// AnnotatedControllerConfigurer.getMappingInfo in spring-graphql

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface PickGraphObject {


    public static final ObjectMapper mapper = new ObjectMapper();

    boolean prettyPrint() default false;

}
