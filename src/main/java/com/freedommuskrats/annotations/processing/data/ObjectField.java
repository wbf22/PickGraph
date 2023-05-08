package com.freedommuskrats.annotations.processing.data;

public class ObjectField {

    private ObjectMapping mapper;

    private String name;

    private Object value;

    public ObjectField(String name) {
        this.name = name;
    }


    public ObjectMapping getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapping mapper) {
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
