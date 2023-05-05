package com.freedommuskrats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import com.freedommuskrats.annotations.processing.data.PickGraphObjectMapper;
import com.freedommuskrats.exception.PickGraphException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class PickGraph {

    private final PickGraphProperties properties;

    private final ObjectMapper objectMapper;

    private AnnotationProcessor annotationProcessor;


    public PickGraph(PickGraphProperties properties, ObjectMapper objectMapper, AnnotationProcessor annotationProcessor) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.annotationProcessor = annotationProcessor;
    }

    public Map<String, Object> fulfillRequest(Object requestObject, Class<?> pickGraphObjectTarget, Map<String, Object> args) {
        Map<String, Object> objMap = objectMapper.convertValue(requestObject, Map.class);
        return execute(objMap, pickGraphObjectTarget, args);
    }

    public Map<String, Object> execute(Map<String, Object> requestedFields, Class<?> pickGraphObjectTarget, Map<String, Object> args) {
        try {
            annotationProcessor.setUp();

            PickGraphObjectMapper pickGraphObjectMapper =
                    annotationProcessor.getPickGraphObjectMapper(pickGraphObjectTarget.getName());

            Object object = pickGraphObjectMapper.invokeMappingMethod(args);
            if (object == null) {
                throw new PickGraphException("Couldn't get " + pickGraphObjectMapper.getTargetClassName() + " from " + objectMapper.writeValueAsString(requestedFields));
            }
            Map<String, Object> objMap = objectMapper.convertValue(object, Map.class);

            Map<String, Object> result = new HashMap<>();
            for (Map.Entry<String, Object> entry : requestedFields.entrySet()) {
                String key = entry.getKey();
                if(objMap.containsKey(key)) {
                    result.put(key, objMap.get(key));
                }
            }

            return fullFillPickGraphObject(requestedFields, result, pickGraphObjectMapper, args);
        } catch (JsonProcessingException e) {
            throw new PickGraphException("Failed due to JSON processing error: " + "\n" + e.getMessage());
        }
    }

    private Map<String, Object> fullFillPickGraphObject(Map<String, Object> requestedFields, Map<String, Object> result, PickGraphObjectMapper pickGraphObjectMapper, Map<String, Object> args) throws JsonProcessingException {
        for (Map.Entry<String, Object> entry : requestedFields.entrySet()) {
            // fulfill the field if it's a PickGraphObject
            String childTargetClassName = entry.getKey();
            if (pickGraphObjectMapper.getChildTargetClassNames().containsKey(childTargetClassName)) {
                PickGraphObjectMapper childPickGraphObjectMapper = annotationProcessor.getPickGraphObjectMapper(
                        pickGraphObjectMapper.getChildTargetClassNames().get(childTargetClassName)
                );

                // get the child object from user mapping method
                Object childObject = childPickGraphObjectMapper.invokeMappingMethod(args);
                if (childObject == null) {
                    throw new PickGraphException("Couldn't get " + pickGraphObjectMapper.getTargetClassName() + " from " + objectMapper.writeValueAsString(requestedFields));
                }
                Map<String, Object> childObjMap = objectMapper.convertValue(childObject, Map.class);

                Map<String, Object> childResult = new HashMap<>();
                Map<String, Object> childFields = (Map<String, Object>) entry.getValue();
                for (Map.Entry<String, Object> entryChild : childFields.entrySet()) {
                    String key = entryChild.getKey();
                    if(childObjMap.containsKey(key)) {
                        childResult.put(key, childObjMap.get(key));
                    }
                }

                // recursively fulfill any PickGraphObjects in the child object
                childResult = fullFillPickGraphObject((Map<String, Object>) entry.getValue(), childResult, childPickGraphObjectMapper, args);

                result.put(entry.getKey(), childResult);
            }
//            else {
//                throw new PickGraphException("Missing @PickGraphMapping annotation for field '" + entry.getKey() + "' which is has the @PickGraphObject annotation.");
//            }
        }
        return result;
    }


}
