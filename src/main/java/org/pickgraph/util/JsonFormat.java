package org.pickgraph.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;

public enum JsonFormat {
    LOWER_CAMEL_CASE,
    UPPER_CAMEL_CASE,
    KEBAB_CASE,
    SNAKE_CASE,
    UPPER_SNAKE_CASE,
    NOT_SPECIFIED;

    public static PropertyNamingStrategy getStrategy(JsonFormat jsonFormat) {
        return switch (jsonFormat) {
            case UPPER_CAMEL_CASE -> PropertyNamingStrategies.UPPER_CAMEL_CASE;
            case KEBAB_CASE -> PropertyNamingStrategies.KEBAB_CASE;
            case SNAKE_CASE -> PropertyNamingStrategies.SNAKE_CASE;
            case UPPER_SNAKE_CASE -> PropertyNamingStrategies.UPPER_SNAKE_CASE;
            default -> PropertyNamingStrategies.LOWER_CAMEL_CASE;
        };
    }

    public static CaseFormat getCaseFormat(JsonFormat jsonFormat) {
        return switch (jsonFormat) {
            case UPPER_CAMEL_CASE -> CaseFormat.UPPER_CAMEL;
            case KEBAB_CASE -> CaseFormat.LOWER_HYPHEN;
            case SNAKE_CASE -> CaseFormat.LOWER_UNDERSCORE;
            case UPPER_SNAKE_CASE -> CaseFormat.UPPER_UNDERSCORE;
            default -> CaseFormat.LOWER_CAMEL;
        };
    }
}
