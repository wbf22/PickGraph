package com.freedommuskrats.annotations.processing.data;

import java.util.HashMap;
import java.util.Map;

public class Endpoint {
    public static final String NO_PATH_SPECIFIED = "NO_PATH_SPECIFIED";

    protected String path;
    protected Object defaultObject;
    protected String requestTypeName;
    protected Class<?> requestType;


    public Endpoint(String path, String requestTypeName, Class<?> requestType) {
        this.path = path;
        this.requestTypeName = requestTypeName;
        this.requestType = requestType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(Object defaultObject) {
        this.defaultObject = defaultObject;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    public Class<?> getRequestType() {
        return requestType;
    }

    public void setRequestType(Class<?> requestType) {
        this.requestType = requestType;
    }
}
