package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class LCPGOMapping {


    @PickGraphMapping
    public LargeChildPGO largeChildMapping() {
        LargeChildPGO largePGO = new LargeChildPGO();
        largePGO.setX1(true);
        largePGO.setX2(new BigDecimal(2));
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

    @SchemaMapping
    public LargeChildPGO x1(LargePGO largePGO) {
        LargeChildPGO largeChildPGO = new LargeChildPGO();
        largeChildPGO.setX1(true);
        largeChildPGO.setX2(new BigDecimal(2));
        largeChildPGO.setX3(3);
        largeChildPGO.setX4(4);
        largeChildPGO.setX5("new Object()");
        largeChildPGO.setX6("6");
        largeChildPGO.setX7("6");
        largeChildPGO.setX8("6");
        largeChildPGO.setX9("6");
        largeChildPGO.setX10("6");
        largeChildPGO.setX11("6");
        largeChildPGO.setX12("6");
        largeChildPGO.setX13("6");
        largeChildPGO.setX14("6");
        largeChildPGO.setX15("6");
        largeChildPGO.setX16("6");
        largeChildPGO.setX17("6");
        largeChildPGO.setX18("6");
        return largeChildPGO;
    }
}
