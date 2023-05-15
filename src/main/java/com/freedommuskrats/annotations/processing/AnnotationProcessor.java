package com.freedommuskrats.annotations.processing;

import com.freedommuskrats.annotations.Schema.IncludeInPickGraphSchema;
import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.annotations.PickGraphMapping;
import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.annotations.processing.data.CustomUserEndpoint;
import com.freedommuskrats.annotations.processing.data.ObjectData;
import com.freedommuskrats.annotations.processing.data.ObjectMapping;
import com.freedommuskrats.annotations.processing.data.UserEndpoint;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AnnotationProcessor implements ApplicationRunner {

    private List<ObjectData> objectDatas = new ArrayList<>();
    private Map<String, ObjectMapping> objectMappings = new HashMap<>();
    private Map<String, UserEndpoint> userEndpoints = new HashMap<>();
    private Map<String, UserEndpoint> userCustomEndpoints = new HashMap<>();
    private ApplicationContext applicationContext;

    public AnnotationProcessor(ApplicationContext context) {
        this.applicationContext = context;
    }

    private boolean initialized = false;

    @Override
    public void run(ApplicationArguments args) {
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

    private void checkClassForAnnotations(String beanClassName, Object bean) {
        try {
            Class<?> beanClass = Class.forName(beanClassName);
            if (beanClass.isAnnotationPresent(PickGraphObject.class)) {
                objectDatas.add(ObjectData.build(beanClass, beanClassName));
            }

            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PickGraphMapping.class)) {
                    objectMappings.put(
                            method.getReturnType().getName(),
                            ObjectMapping.build(
                                beanClass.getName(), method.getName(), method.getReturnType(), method, bean
                            )
                    );
                }
                if (method.isAnnotationPresent(PickEndpoint.class)) {
                    UserEndpoint userEndpoint = UserEndpoint.build(method);
                    userEndpoints.put(userEndpoint.getRequestTypeName(), userEndpoint);
                }
                if (method.isAnnotationPresent(IncludeInPickGraphSchema.class)) {
                    CustomUserEndpoint customUserEndpoint = CustomUserEndpoint.build(method);
                }
            }
        } catch (ClassNotFoundException e) {
            // Ignore this exception
        }
    }

    public ObjectMapping getPickGraphObjectMapper(String className) {
        return objectMappings.get(className);
    }

    public List<ObjectData> getObjectDatas() {
        return objectDatas;
    }

    public Map<String, ObjectMapping> getObjectMappings() {
        return objectMappings;
    }

    public Map<String, UserEndpoint> getUserEndpoints() {
        return userEndpoints;
    }

    public void getSchema() {

    }
}
