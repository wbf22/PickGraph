package com.freedommuskrats.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping(method = RequestMethod.POST)
public @interface PickEndpoint {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
