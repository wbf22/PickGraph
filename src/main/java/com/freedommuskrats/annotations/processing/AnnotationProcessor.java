package com.freedommuskrats.annotations.processing;

import com.freedommuskrats.PickGraphAutoConfig;
import com.freedommuskrats.annotations.PickGraphMapping;
import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.annotations.processing.data.PickGraphObjectData;
import com.freedommuskrats.annotations.processing.data.PickGraphObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationProcessor {

    private List<PickGraphObjectData> objectDatas = new ArrayList<>();
    private Map<String, PickGraphObjectMapper> objectMappings = new HashMap<>();
    private boolean intiialized = false;


    public void setup() {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext()) {
            applicationContext.register(PickGraphAutoConfig.class);
            applicationContext.refresh();
            scanClasses(applicationContext);
            intiialized = true;
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

    public boolean isIntialized() {
        return intiialized;
    }
}
