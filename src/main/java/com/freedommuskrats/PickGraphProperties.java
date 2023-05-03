package com.freedommuskrats;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pick.graph")
public class PickGraphProperties {

    public static final String SNAKE_CASE = "SNAKE_CASE";
    public static final String LOWER_CAMEL_CASE = "LOWER_CAMEL_CASE";

    private String jsonFormat;

    private boolean playground = true;

    public String getJsonFormat() {
        return jsonFormat;
    }

    public void setJsonFormat(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    public boolean isPlayground() {
        return playground;
    }

    public void setPlayground(boolean playground) {
        this.playground = playground;
    }
}
