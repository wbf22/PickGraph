package org.pickgraph.annotations.Schema;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IncludeInPickGraphSchema {
    Class<?> returnPickGraphObject();

}
