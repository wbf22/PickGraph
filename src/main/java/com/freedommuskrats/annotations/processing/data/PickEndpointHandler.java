package com.freedommuskrats.annotations.processing.data;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PickEndpointHandler implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) {
        // Add your implementation here
        return null;
    }
}