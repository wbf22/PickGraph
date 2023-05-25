package org.pickgraph.annotations.processing.data;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.pickgraph.exception.PickGraphException;
import org.pickgraph.util.JsonFormat;

import java.lang.reflect.Method;

public class UserEndpoint extends Endpoint {

    private JsonFormat jsonFormat;


    private UserEndpoint(String path, String requestTypeName, Class<?> requestType, JsonFormat jsonFormat) {
        super(path, requestTypeName, requestType);
        this.jsonFormat = jsonFormat;
    }



    public static UserEndpoint build(Method method, String basePickGraphPath) {
        if (method.isAnnotationPresent(PickGraphMapping.class)) {
            PickEndpoint annotation = method.getDeclaredAnnotation(PickEndpoint.class);
            String type = (annotation.path().equals(NO_PATH_SPECIFIED))? method.getReturnType().getSimpleName() : annotation.path().replace("/", "");
            String path = basePickGraphPath + "/" + type;
            return new UserEndpoint(path, type, method.getReturnType(), annotation.jsonFormat());
        }
        else {
            throw new PickGraphException("@PickEndpoint annotation must be on the same" +
                    "@PickGraphMapping annotated method you intend to fulfill the request");
        }
    }


    public JsonFormat getJsonFormat() {
        return jsonFormat;
    }
}
