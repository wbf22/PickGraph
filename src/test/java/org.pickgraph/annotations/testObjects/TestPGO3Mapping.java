package org.pickgraph.annotations.testObjects;

import org.pickgraph.annotations.PickGraphMapping;
import org.springframework.stereotype.Component;

@Component
public class TestPGO3Mapping {


    @PickGraphMapping
    public TestPGO3 testMethod() {
        return new TestPGO3();
    }


}
