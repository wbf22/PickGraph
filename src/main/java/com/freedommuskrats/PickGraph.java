package com.freedommuskrats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommuskrats.annotations.PickGraphObject;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import com.freedommuskrats.annotations.processing.data.Endpoint;
import com.freedommuskrats.annotations.processing.data.ObjectMapping;
import com.freedommuskrats.config.PickGraphProperties;
import com.freedommuskrats.exception.PickGraphException;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PickGraph {

    private final PickGraphProperties properties;

    private AnnotationProcessor annotationProcessor;

    private ObjectMapper objectMapper;


    public PickGraph(PickGraphProperties properties, AnnotationProcessor annotationProcessor) {
        this.properties = properties;
        this.objectMapper = new ObjectMapper();
        this.annotationProcessor = annotationProcessor;
    }

    public Map<String, Object> fulfillRequest(Map<String, Object> requestFields, Class<?> pickGraphObjectTarget) {
        return execute(requestFields, pickGraphObjectTarget, new HashMap<>(), null);
    }

    public Map<String, Object> fulfillRequest(Map<String, Object> requestFields, Class<?> pickGraphObjectTarget, String jsonFormat) {
        return execute(requestFields, pickGraphObjectTarget, new HashMap<>(), jsonFormat);
    }

    public Map<String, Object> fulfillRequest(Map<String, Object> requestFields, Class<?> pickGraphObjectTarget, Map<String, Object> args) {
        return execute(requestFields, pickGraphObjectTarget, args, null);
    }

    public Map<String, Object> fulfillRequest(Map<String, Object> requestFields, Class<?> pickGraphObjectTarget, Map<String, Object> args, String jsonFormat) {
        return execute(requestFields, pickGraphObjectTarget, args, jsonFormat);
    }

    public Map<String, Object> execute(Map<String, Object> requestedFields, Class<?> pickGraphObjectTarget, Map<String, Object> args, String jsonFormat) {
        try {
            CaseFormat caseFormat = properties.getCaseFormatOrProjectDefault(jsonFormat);
            objectMapper.setPropertyNamingStrategy(properties.getStrategyOrProjectDefault(jsonFormat));

            ObjectMapping pickGraphObjectMapper =
                    annotationProcessor.getPickGraphObjectMapper(pickGraphObjectTarget.getName());

            Object object = pickGraphObjectMapper.invokeMappingMethod(args);
            if (object == null) {
                throw new PickGraphException("Couldn't get " + pickGraphObjectMapper.getTargetClassName() + " from " + objectMapper.writeValueAsString(requestedFields));
            }

            return traverseObject(requestedFields, object, args, caseFormat);

        } catch (IllegalAccessException | JsonProcessingException e) {
            throw new PickGraphException("Failed due to class processing error for object: " + pickGraphObjectTarget.getName() + "\n" + e.getMessage());
        }
    }

    public Map<String, Object> traverseObject(Map<String, Object> requestedFields, Object obj, Map<String, Object> args, CaseFormat caseformat) throws IllegalAccessException, JsonProcessingException {
        Map<String, Object> result = new HashMap<>();

        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            if (requestedFields.containsKey(field.getName())) {
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                field.setAccessible(false);

                // If the field value is not null and not a primitive type, recursively traverse the object
                if (field.getType().isAnnotationPresent(PickGraphObject.class)) {
                    if( fieldValue != null) {
                        throw new PickGraphException("It looks like you populated a field of " +
                                "the object you intended to be handled by a PickGraphObject and PickGraphMapping" +
                                " for your object" + field.getType() + ". Remove the @PickGraphObject tag on your " +
                                "object or avoid populating it in your mapping method.");
                    }
                    else {
                        ObjectMapping pickGraphObjectMapper = annotationProcessor.getPickGraphObjectMapper(
                                field.getType().getName()
                        );
                        Object object = pickGraphObjectMapper.invokeMappingMethod(args);
                        if (object == null) {
                            throw new PickGraphException("Couldn't get " + pickGraphObjectMapper.getTargetClassName() + " from " + objectMapper.writeValueAsString(requestedFields));
                        }
                        fieldValue = traverseObject((Map<String, Object>) requestedFields.get(field.getName()), object, args, caseformat);
                    }
                }
                else if (fieldValue instanceof Map<?,?>) {
                    fieldValue = convertMapCase((Map<String, Object>) fieldValue, caseformat);
                }
                else if ( !fieldValue.getClass().isPrimitive() &&
                        !(fieldValue instanceof String) &&
                        !(fieldValue instanceof Number) &&
                        !(fieldValue instanceof Boolean)
                ) {
                    fieldValue = objectMapper.writeValueAsString(fieldValue);
                }
                result.put(CaseFormat.LOWER_CAMEL.to(caseformat, field.getName()), fieldValue);
            }
        }


        return result;
    }

    private static Map<String, Object> convertMapCase(Map<String, Object> map, CaseFormat newFormat) {
        if (map == null) {
            return null;
        }
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry: map.entrySet()) {
            String newKey = CaseFormat.LOWER_CAMEL.to(newFormat, entry.getKey());
            if (entry.getValue() instanceof String asString) {
                newMap.put(
                        newKey,
                        CaseFormat.LOWER_CAMEL.to(newFormat, asString)
                );
            } else if (entry.getValue() instanceof Map m) {
                newMap.put(
                        newKey,
                        convertMapCase(m, newFormat)
                );
            } else {
                newMap.put(newKey, entry.getValue());
            }
        }
        return newMap;
    }



    public List<Endpoint> getSchema() {
        return annotationProcessor.getSchema();
    }
}
