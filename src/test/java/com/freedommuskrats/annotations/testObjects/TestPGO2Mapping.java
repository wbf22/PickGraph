package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.config.PickGraphProperties;
import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPGO2Mapping {


    @PickEndpoint(jsonFormat = PickGraphProperties.SNAKE_CASE)
    @PickGraphMapping
    public TestPGO2 testMethod() {
        return new TestPGO2("jill", null);
    }


}
