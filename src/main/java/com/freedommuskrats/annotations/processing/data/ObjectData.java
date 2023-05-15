package com.freedommuskrats.annotations.processing.data;

import com.freedommuskrats.annotations.Schema.SchemaDescription;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

import static com.freedommuskrats.annotations.processing.data.JsonBuilder.*;

public class ObjectData {


    public ObjectData(String name) {
        this.name = name;
    }

    private String name;
    private Map<String, ObjectField> fields = new HashMap<>();
    private Object defaultObject;


    public static ObjectData build(Class<?> beanClass, String beanClassName) {
        ObjectData data = new ObjectData(beanClassName);
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {

            Object blankObject = buildField(field).getDefaultObject();

            data.addField(
                    field.getName(),
                    new ObjectField(
                            field.getType(),
                            blankObject,
                            getDescription(field)
                    )
            );

        }

        data.setDefaultObject(
                (isJavaType(beanClass))? resolveJavaClass(beanClass) : resolveNonBasicClass(beanClass)
        );
        return data;
    }

    private static String getDescription(Field field) {
        if (field.isAnnotationPresent(SchemaDescription.class)) {
            return field.getAnnotation(SchemaDescription.class).value();
        }
        return field.getType().getSimpleName();
    }

    private boolean isStandardJavaClass(Object obj) {
        return obj.getClass().getName().startsWith("java.");
    }
    
    public static boolean isNumericClass(Class<?> type) {
        return Number.class.isAssignableFrom(type) || isPrimitiveNumericClass(type);
    }

    public static boolean isPrimitiveNumericClass(Class<?> type) {
        return type == int.class || type == long.class || type == double.class
                || type == float.class || type == short.class || type == byte.class;
    }

    public static boolean isDecimalType(Class<?> type) {
        return type == double.class || type == Double.class || type == float.class
                || type == Float.class || type == BigDecimal.class;
    }

    public Map<String, ObjectField> getFields() {
        return fields;
    }

    public void setFields(Map<String, ObjectField> fields) {
        this.fields = fields;
    }

    public Object getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(Object defaultObject) {
        this.defaultObject = defaultObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectField getField(String name) {
        return fields.get(name);
    }
    public void addField(String name, ObjectField field) {
        fields.put(name, field);
    }
}
