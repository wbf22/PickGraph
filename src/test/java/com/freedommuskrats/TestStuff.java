package com.freedommuskrats;

import org.junit.jupiter.api.Test;

public class TestStuff {

    @Test
    void test() {
        Object obj = false;
        Boolean b = (Boolean) obj;
        System.out.println( (boolean) b);
    }
}
