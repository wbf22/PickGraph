package org.pickgraph.annotations.processing.data;

public class ObjectField {
    private Class<?> type;
    private Object defaultObject;
    private String description;


    public ObjectField(Class<?> type, Object defaultObject, String description) {
        this.type = type;
        this.defaultObject = defaultObject;
        this.description = description;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(Object defaultObject) {
        this.defaultObject = defaultObject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
