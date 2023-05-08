package com.freedommuskrats.annotations.processing.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.freedommuskrats.config.PickGraphProperties;
import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.annotations.PickGraphMapping;
import com.freedommuskrats.exception.PickGraphException;

import java.lang.reflect.Method;

import static com.freedommuskrats.config.PickGraphProperties.getStrategy;

public class UserEndpoint {
    public static final String NO_PATH_SPECIFIED = "NO_PATH_SPECIFIED";

    private String requestTypeName;
    private Class<?> requestType;
    private String jsonFormat;


    private UserEndpoint(String requestTypeName, Class<?> requestType, String jsonFormat) {
        this.requestTypeName = requestTypeName;
        this.requestType = requestType;
        this.jsonFormat = jsonFormat;
    }



    public static UserEndpoint build(Method method) {
        if (method.isAnnotationPresent(PickGraphMapping.class)) {
            PickEndpoint annotation = method.getDeclaredAnnotation(PickEndpoint.class);
            String type = (annotation.name().equals(NO_PATH_SPECIFIED))? method.getReturnType().getSimpleName() : annotation.name();
            return new UserEndpoint(type, method.getReturnType(), annotation.jsonFormat());
        }
        else {
            throw new PickGraphException("@PickEndpoint annotation must be on the same" +
                    "@PickGraphMapping annotated method you intend to fulfill the request");
        }
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public Class<?> getRequestType() {
        return requestType;
    }

    public String getJsonFormat() {
        return jsonFormat;
    }
}
