package com.freedommuskrats.annotations.processing.data;

import java.util.HashMap;
import java.util.Map;

public class SchemaObject {

    private Map<String, Object> fields = new HashMap<>();


    public Map<String, Object> getFields() {
        return fields;
    }

    public void addField(String name, Object object) {
        fields.put(name, object);
    }
}
