package com.freedommuskrats.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPGO2Mapping {


    @PickGraphMapping
    public TestPGO2 testMethod() {
        return new TestPGO2("jill", null);
    }


}
