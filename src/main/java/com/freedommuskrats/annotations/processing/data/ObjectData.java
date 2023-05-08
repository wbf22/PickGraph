package com.freedommuskrats.annotations.processing.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectData {


    public ObjectData(String name) {
        this.name = name;
    }

    private String name;
    private Map<String, ObjectField> fields = new HashMap<>();

    public static ObjectData build(Class<?> beanClass, String beanClassName) {
        ObjectData data = new ObjectData(beanClassName);
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            data.addField(field.getName(), new ObjectField(field.getName()));
        }
        return data;
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
