package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPickGraphObjectMapping {


    @PickGraphMapping
    public TestPickGraphObject testMethod(int junk) {
        return new TestPickGraphObject("fred", 10, null);
    }


}
