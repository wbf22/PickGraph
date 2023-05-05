package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPGO3Mapping {


    @PickGraphMapping
    public TestPGO3 testMethod() {
        return new TestPGO3();
    }


}
