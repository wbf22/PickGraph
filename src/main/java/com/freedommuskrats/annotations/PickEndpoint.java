package com.freedommuskrats.annotations;

import java.lang.annotation.*;

import static com.freedommuskrats.annotations.processing.data.UserEndpoint.NO_PATH_SPECIFIED;
import static com.freedommuskrats.config.PickGraphProperties.NOT_SPECIFIED;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PickEndpoint {
    String name() default NO_PATH_SPECIFIED;
    String jsonFormat() default NOT_SPECIFIED;
}
