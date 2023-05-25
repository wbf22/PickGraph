package org.pickgraph.annotations.testObjects;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LPGOMapping {


    @PickEndpoint
    @PickGraphMapping
    public LargePGO largeEndpoint() {
        LargePGO largePGO = new LargePGO();
        largePGO.setX2(2);
        largePGO.setX3(3);
        largePGO.setX4(4);
        largePGO.setX5("new Object()");
        largePGO.setX6("6");
        largePGO.setX7("6");
        largePGO.setX8("6");
        largePGO.setX9("6");
        largePGO.setX10("6");
        largePGO.setX11("6");
        largePGO.setX12("6");
        largePGO.setX13("6");
        largePGO.setX14("6");
        largePGO.setX15("6");
        largePGO.setX16("6");
        largePGO.setX17("6");
        largePGO.setX18("6");
        return largePGO;
    }

    @QueryMapping("largePGO")
    public LargePGO largePGO() {
        LargePGO largePGO = new LargePGO();
        largePGO.setX2(2);
        largePGO.setX3(3);
        largePGO.setX4(4);
        largePGO.setX5("new Object()");
        largePGO.setX6("6");
        largePGO.setX7("6");
        largePGO.setX8("6");
        largePGO.setX9("6");
        largePGO.setX10("6");
        largePGO.setX11("6");
        largePGO.setX12("6");
        largePGO.setX13("6");
        largePGO.setX14("6");
        largePGO.setX15("6");
        largePGO.setX16("6");
        largePGO.setX17("6");
        largePGO.setX18("6");
        return largePGO;
    }
}
