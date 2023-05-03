package com.freedommuskrats.annotations.processing.data;

public class PickGraphObjectField {

    private PickGraphObjectMapper mapper;

    private String name;

    private Object value;

    public PickGraphObjectField(String name) {
        this.name = name;
    }


    public PickGraphObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(PickGraphObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
