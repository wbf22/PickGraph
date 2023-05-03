package com.freedommuskrats.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPGO3Mapping {


    @PickGraphMapping
    public TestPGO3 testMethod(TestPickGraphObject junk) {
        return new TestPGO3();
    }


}
