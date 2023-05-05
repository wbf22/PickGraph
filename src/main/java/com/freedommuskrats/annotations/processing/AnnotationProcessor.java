package com.freedommuskrats.annotations.processing;

import com.freedommuskrats.annotations.PickGraphMapping;
import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.annotations.processing.data.PickGraphObjectData;
import com.freedommuskrats.annotations.processing.data.PickGraphObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AnnotationProcessor{

    private List<PickGraphObjectData> objectDatas = new ArrayList<>();
    private Map<String, PickGraphObjectMapper> objectMappings = new HashMap<>();
    private ApplicationContext applicationContext;

    public AnnotationProcessor(ApplicationContext context) {
        this.applicationContext = context;
    }

    private boolean initialized = false;
    public void setUp() {
        if (!initialized) {
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                if (applicationContext.containsBeanDefinition(beanName)) {
                    Object bean = applicationContext.getBean(beanName);
                    String beanClassName = bean.getClass().getName();
                    checkClassForAnnotations(beanClassName, bean);
                }
            }
            initialized = true;
        }
    }

    private void scanClasses(AnnotationConfigApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            if (applicationContext.containsBeanDefinition(beanName)) {
                BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanName);
                Object bean = applicationContext.getBean(beanName);
                String beanClassName = beanDefinition.getBeanClassName();
                if (beanClassName != null) {
                    checkClassForAnnotations(beanClassName, bean);
                }
            }
        }
    }

    private void checkClassForAnnotations(String beanClassName, Object bean) {
        try {
            Class<?> beanClass = Class.forName(beanClassName);
            if (beanClass.isAnnotationPresent(PickGraphObject.class)) {
                objectDatas.add(PickGraphObjectData.build(beanClass, beanClassName));
            }

            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PickGraphMapping.class)) {
                    objectMappings.put(
                            method.getReturnType().getName(),
                            PickGraphObjectMapper.build(
                                beanClass.getName(), method.getName(), method.getReturnType(), method, bean
                            )
                    );
                }
            }
        } catch (ClassNotFoundException e) {
            // Ignore this exception
        }
    }


    public PickGraphObjectMapper getPickGraphObjectMapper(String className) {
        return objectMappings.get(className);
    }

    public List<PickGraphObjectData> getObjectDatas() {
        return objectDatas;
    }

    public Map<String, PickGraphObjectMapper> getObjectMappings() {
        return objectMappings;
    }



}
