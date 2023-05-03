package com.freedommuskrats.annotations;

import com.freedommuskrats.PickGraphAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(PickGraphAutoConfig.class)
public @interface EnablePickGraph {
}
