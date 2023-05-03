package com.freedommuskrats.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPickGraphObjectMapping {


    @PickGraphMapping
    public TestPickGraphObject testMethod(TestPickGraphObject junk) {
        return new TestPickGraphObject("fred", 10, null);
    }


}
