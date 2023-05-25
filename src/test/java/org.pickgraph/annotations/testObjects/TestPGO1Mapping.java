package org.pickgraph.annotations.testObjects;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestPGO1Mapping {


    @PickEndpoint
    @PickGraphMapping
    public TestPGO1 testMethod(int junk) {
        return new TestPGO1("fred", junk, null);
    }


}
