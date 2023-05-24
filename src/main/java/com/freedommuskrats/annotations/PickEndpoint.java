package com.freedommuskrats.annotations;

import java.lang.annotation.*;

import static com.freedommuskrats.annotations.processing.data.Endpoint.NO_PATH_SPECIFIED;
import static com.freedommuskrats.config.PickGraphProperties.NOT_SPECIFIED;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PickEndpoint {
    String path() default NO_PATH_SPECIFIED;
    String jsonFormat() default NOT_SPECIFIED;
}
