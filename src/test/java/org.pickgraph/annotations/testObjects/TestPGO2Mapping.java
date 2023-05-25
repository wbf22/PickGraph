package org.pickgraph.annotations.testObjects;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.pickgraph.config.PickGraphProperties;
import org.springframework.stereotype.Component;

import static org.pickgraph.util.JsonFormat.SNAKE_CASE;

@Component
public class TestPGO2Mapping {


    @PickEndpoint(jsonFormat = SNAKE_CASE)
    @PickGraphMapping
    public TestPGO2 testMethod() {
        return new TestPGO2("jill", null);
    }


}
