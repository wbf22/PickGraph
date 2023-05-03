package com.freedommuskrats.annotations.processing.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PickGraphObjectData {


    public PickGraphObjectData(String name) {
        this.name = name;
    }

    private String name;
    private Map<String, PickGraphObjectField> fields = new HashMap<>();

    public static PickGraphObjectData build(Class<?> beanClass, String beanClassName) {
        PickGraphObjectData data = new PickGraphObjectData(beanClassName);
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            data.addField(field.getName(), new PickGraphObjectField(field.getName()));
        }
        return data;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PickGraphObjectField getField(String name) {
        return fields.get(name);
    }
    public void addField(String name, PickGraphObjectField field) {
        fields.put(name, field);
    }
}
