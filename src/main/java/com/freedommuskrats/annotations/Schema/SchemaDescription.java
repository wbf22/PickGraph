package com.freedommuskrats.annotations.Schema;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SchemaDescription {
    String value();
}
