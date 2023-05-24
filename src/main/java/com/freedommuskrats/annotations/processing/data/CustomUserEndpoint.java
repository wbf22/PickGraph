package com.freedommuskrats.annotations.processing.data;

import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.annotations.Schema.IncludeInPickGraphSchema;
import com.freedommuskrats.exception.PickGraphException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.text.Annotation;
import java.util.Arrays;

public class CustomUserEndpoint extends Endpoint {

    private CustomUserEndpoint(String path, String requestTypeName, Class<?> requestType) {
        super(path, requestTypeName, requestType);
    }

    public static CustomUserEndpoint build(Method method) {
        Class<?> targetClass = method.getAnnotation(IncludeInPickGraphSchema.class).returnPickGraphObject();
        boolean req = method.isAnnotationPresent(RequestMapping.class);
        boolean get = method.isAnnotationPresent(GetMapping.class);
        boolean post = method.isAnnotationPresent(PostMapping.class);
        boolean put = method.isAnnotationPresent(PutMapping.class);

        if (targetClass.isAnnotationPresent(PickGraphObject.class) && (req || get || post || put) ) {
            String[] pathBits;
            if (req) {
                pathBits = method.getAnnotation(RequestMapping.class).path();
            }
            else if (get) {
                pathBits = method.getAnnotation(GetMapping.class).path();
            }
            else if (post) {
                pathBits = method.getAnnotation(PostMapping.class).path();
            }
            else {
                pathBits = method.getAnnotation(PutMapping.class).path();
            }

            StringBuilder path = new StringBuilder();
            for (String p : pathBits) {
                path.append(p);
            }
            return new CustomUserEndpoint(path.toString(), targetClass.getSimpleName(), targetClass);
        }
        else {
            throw new PickGraphException("@IncludeInPickGraphSchema annotation must return a" +
                    "@PickGraphObject annotated object for the schema to be built, and must be " +
                    "place on a rest endpoint with an annotation like @GetMapping, @PostMapping, etc.");
        }
    }


}
