package org.pickgraph.annotations;

import org.pickgraph.annotations.processing.data.Endpoint;
import org.pickgraph.util.JsonFormat;

import java.lang.annotation.*;

import static org.pickgraph.util.JsonFormat.NOT_SPECIFIED;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PickEndpoint {
    String path() default Endpoint.NO_PATH_SPECIFIED;
    JsonFormat jsonFormat() default NOT_SPECIFIED;
}
