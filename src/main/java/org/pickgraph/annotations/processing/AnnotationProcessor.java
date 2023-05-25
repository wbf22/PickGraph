package org.pickgraph.annotations.processing;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.pickgraph.annotations.PickGraphObject;
import org.pickgraph.annotations.Schema.IncludeInPickGraphSchema;
import org.pickgraph.annotations.processing.data.*;
import org.pickgraph.config.PickGraphProperties;
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

    private Map<Class<?>, ObjectData> objectDatas = new HashMap<>();
    private Map<String, ObjectMapping> objectMappings = new HashMap<>();
    private Map<String, UserEndpoint> userEndpoints = new HashMap<>();
    private Map<String, CustomUserEndpoint> userCustomEndpoints = new HashMap<>();
    private List<Endpoint> schema = new ArrayList<>();

    private ApplicationContext applicationContext;


    private String basePickGraphPath;

    public AnnotationProcessor(ApplicationContext context, PickGraphProperties properties) {
        this.applicationContext = context;
        this.basePickGraphPath = properties.getEndpointPath();
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
            buildSchema();
        }
    }

    private void checkClassForAnnotations(String beanClassName, Object bean) {
        try {
            Class<?> beanClass = Class.forName(beanClassName);
            if (beanClass.isAnnotationPresent(PickGraphObject.class)) {
                objectDatas.put(bean.getClass(), ObjectData.build(beanClass, beanClassName));
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
                    UserEndpoint userEndpoint = UserEndpoint.build(method, basePickGraphPath);
                    userEndpoints.put(userEndpoint.getRequestTypeName(), userEndpoint);
                }
                if (method.isAnnotationPresent(IncludeInPickGraphSchema.class)) {
                    CustomUserEndpoint customUserEndpoint = CustomUserEndpoint.build(method);
                    userCustomEndpoints.put(customUserEndpoint.getRequestTypeName(), customUserEndpoint);
                }
            }
        } catch (ClassNotFoundException e) {
            // Ignore this exception
        }
    }

    public ObjectMapping getPickGraphObjectMapper(String className) {
        return objectMappings.get(className);
    }

    public Map<Class<?>, ObjectData> getObjectDatas() {
        return objectDatas;
    }

    public Map<String, ObjectMapping> getObjectMappings() {
        return objectMappings;
    }

    public Map<String, UserEndpoint> getUserEndpoints() {
        return userEndpoints;
    }


    public void buildSchema() {
        List<Endpoint> newList = new ArrayList<>();
        newList.addAll(userEndpoints.values().stream().toList());
        newList.addAll(userCustomEndpoints.values().stream().toList());

        for (Endpoint end : newList) {
            end.setDefaultObject(
                    objectDatas.get(end.getRequestType()).getDefaultObject()
            );
            schema.add(end);
        }
    }

    public List<Endpoint> getSchema() {
        return schema;
    }
}
