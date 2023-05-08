package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestPGO1Mapping {


    @PickEndpoint
    @PickGraphMapping
    public TestPGO1 testMethod(int junk) {
        return new TestPGO1("fred", junk, null);
    }


}
