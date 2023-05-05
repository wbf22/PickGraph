package com.freedommuskrats.annotations.processing.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.exception.PickGraphException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class PickGraphObjectMapper {

    private String targetClassName;
    private Method method;
    private Object beanInstance;
    private Map<String, String> childTargetClassNames;
    private Class<?> targetClass;

    private ObjectMapper objectMapper;


    public PickGraphObjectMapper(String targetClassName, Method method, Object beanInstance, Map<String, String> childTargetClassNames, Class<?> targetClass) {
        this.targetClassName = targetClassName;
        this.method = method;
        this.beanInstance = beanInstance;
        this.childTargetClassNames = childTargetClassNames;
        this.targetClass = targetClass;
    }

    public static PickGraphObjectMapper build(
            String mappingClass,
            String mappingMethodName,
            Class<?> targetClass,
            Method method,
            Object beanInstance
    ) {
        String targetClassName = targetClass.getName();
        if (!targetClass.isAnnotationPresent(PickGraphObject.class)) {
            throw new PickGraphException(
                    targetClassName + " as return type for "
                        + mappingClass + " " + mappingMethodName
                        + " needs to have the @PickGraphObject annotation."
            );
        }

        Map<String, String> childTargetClassNames = new HashMap<>();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            if ( field.getType().isAnnotationPresent(PickGraphObject.class) ) {
                childTargetClassNames.put(field.getName(), field.getType().getName());
            }
        }

        return new PickGraphObjectMapper(targetClassName, method, beanInstance, childTargetClassNames, targetClass);
    }

    public Object invokeMappingMethod(Map<String, Object> args) {
        try {
            List<Object> argsList = new ArrayList<>();
            for (Parameter p : method.getParameters()) {
                if (args.containsKey(p.getName())) {
                    argsList.add(args.get(p.getName()));
                }
            }

            return method.invoke(beanInstance, argsList.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PickGraphException("Internal PickGraph error calling mapping method '" + method.getName() + "'\n" + e.getMessage());
        } catch (RuntimeException e) {
            StringBuilder message = new StringBuilder("Internal PickGraph error calling @PickGraphMapping method '"
                    + method.getName() + "'.\n\t Method expected types: \n\t\t");
            Arrays.stream(method.getParameterTypes()).sequential().forEach(type -> message.append(type + "\n\t\t"));
            message.append("\n\t but got types: \n\t\t");
            args.forEach((key, value) -> {
                message.append(key + ":" + value.getClass().getName() + "\n\t\t");
            });
            message.append("\n\t You're probably missing an argument in your call to PickGraph, have a duplicate argument name, or the wrong arguement name.\n\t");
            message.append(" Check your @PickGraphMapping methods or your @PickEndpoints or your calls of Pickgraph.fulfillRequest(...)\n\n");
            throw new PickGraphException(message + e.getMessage());
        }
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    public Map<String, String> getChildTargetClassNames() {
        return childTargetClassNames;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
