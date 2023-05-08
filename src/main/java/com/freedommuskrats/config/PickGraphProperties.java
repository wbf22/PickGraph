package com.freedommuskrats.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pick.graph")
public class PickGraphProperties {

    public static final String LOWER_CAMEL_CASE = "LOWER_CAMEL_CASE";
    public static final String UPPER_CAMEL_CASE = "UPPER_CAMEL_CASE";
    public static final String KEBAB_CASE = "KEBAB_CASE";
    public static final String SNAKE_CASE = "SNAKE_CASE";
    public static final String UPPER_SNAKE_CASE = "UPPER_SNAKE_CASE";
    public static final String NOT_SPECIFIED = "NOT_SPECIFIED";

    private String jsonFormat = LOWER_CAMEL_CASE;

    private boolean playground = true;

    private String endpointPath = "/pick";


    public String getEndpointPath() {
        return endpointPath;
    }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

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

    public static PropertyNamingStrategy getStrategy(String jsonFormat) {
        return switch (jsonFormat) {
            case PickGraphProperties.UPPER_CAMEL_CASE -> PropertyNamingStrategies.UPPER_CAMEL_CASE;
            case PickGraphProperties.KEBAB_CASE -> PropertyNamingStrategies.KEBAB_CASE;
            case PickGraphProperties.SNAKE_CASE -> PropertyNamingStrategies.SNAKE_CASE;
            case PickGraphProperties.UPPER_SNAKE_CASE -> PropertyNamingStrategies.UPPER_SNAKE_CASE;
            default -> PropertyNamingStrategies.LOWER_CAMEL_CASE;
        };
    }

    public static CaseFormat getCaseFormat(String jsonFormat) {
        return switch (jsonFormat) {
            case PickGraphProperties.UPPER_CAMEL_CASE -> CaseFormat.UPPER_CAMEL;
            case PickGraphProperties.KEBAB_CASE -> CaseFormat.LOWER_HYPHEN;
            case PickGraphProperties.SNAKE_CASE -> CaseFormat.LOWER_UNDERSCORE;
            case PickGraphProperties.UPPER_SNAKE_CASE -> CaseFormat.UPPER_UNDERSCORE;
            default -> CaseFormat.LOWER_CAMEL;
        };
    }

    public PropertyNamingStrategy getStrategyOrProjectDefault(String format) {
        if (format == null || format.equals(NOT_SPECIFIED)) {
            return getStrategy(jsonFormat);
        } else {
            return getStrategy(format);
        }
    }

    public CaseFormat getCaseFormatOrProjectDefault(String format) {
        if (format == null || format.equals(NOT_SPECIFIED)) {
            return getCaseFormat(jsonFormat);
        } else {
            return getCaseFormat(format);
        }
    }
}