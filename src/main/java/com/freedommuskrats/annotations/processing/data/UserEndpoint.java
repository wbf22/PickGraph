package com.freedommuskrats.annotations.processing.data;

import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.annotations.PickGraphMapping;
import com.freedommuskrats.exception.PickGraphException;

import java.lang.reflect.Method;

public class UserEndpoint extends Endpoint {

    private String jsonFormat;


    private UserEndpoint(String path, String requestTypeName, Class<?> requestType, String jsonFormat) {
        super(path, requestTypeName, requestType);
        this.jsonFormat = jsonFormat;
    }



    public static UserEndpoint build(Method method, String basePickGraphPath) {
        if (method.isAnnotationPresent(PickGraphMapping.class)) {
            PickEndpoint annotation = method.getDeclaredAnnotation(PickEndpoint.class);
            String type = (annotation.path().equals(NO_PATH_SPECIFIED))? method.getReturnType().getSimpleName() : annotation.path();
            String path = basePickGraphPath + "/" + type;
            return new UserEndpoint(path, type, method.getReturnType(), annotation.jsonFormat());
        }
        else {
            throw new PickGraphException("@PickEndpoint annotation must be on the same" +
                    "@PickGraphMapping annotated method you intend to fulfill the request");
        }
    }


    public String getJsonFormat() {
        return jsonFormat;
    }
}
