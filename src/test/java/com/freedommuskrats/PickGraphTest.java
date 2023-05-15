package com.freedommuskrats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.freedommuskrats.annotations.testObjects.LargeChildPGO;
import com.freedommuskrats.annotations.testObjects.LargePGO;
import com.freedommuskrats.annotations.testObjects.TestPGO1;
import com.freedommuskrats.config.PickGraphAutoConfig;
import com.freedommuskrats.config.PickGraphProperties;
import com.freedommuskrats.util.Timer;
import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.freedommuskrats.util.Timer.start;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = PickGraphAutoConfig.class)
class PickGraphTest {

    @Autowired
    private PickGraph pickGraph;

    @Test
    void test_execute_doubleNested() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("age", null);

        Map<String, Object> fields2 = new HashMap<>();
        fields2.put("name", null);

        Map<String, Object> fields3 = Map.of("map", "");

        fields2.put("testPGO3", fields3);
        fields.put("testPGO2", fields2);

        Map<String, Object> result = pickGraph.execute(fields, TestPGO1.class, Map.of("junk", 10), null);

        assertNotNull(result);

        assertFalse(result.containsKey("name"));
        assertEquals(10, result.get("age"));

        Map<String, Object> testPGO2 = (Map<String, Object>) result.get("testPGO2");
        assertNotNull(testPGO2);
        assertFalse(testPGO2.containsKey("description"));
        assertEquals("jill", testPGO2.get("name"));

        Map<String, Object> testPGO3 = (Map<String, Object>) testPGO2.get("testPGO3");
        assertNotNull(testPGO3);
        assertTrue(testPGO3.containsKey("map"));
    }

    @Test
    void test_execute_largeObject() {

        Map<String, Object> lfields = new HashMap<>();
        lfields.put("x2", null);
        lfields.put("x3", null);
        lfields.put("x4", null);
        lfields.put("x5", null);
        lfields.put("x6", null);
        lfields.put("x7", null);
        lfields.put("x8", null);
        lfields.put("x9", null);
        lfields.put("x10", null);
        lfields.put("x11", null);
        lfields.put("x12", null);
        lfields.put("x13", null);
        lfields.put("x14", null);

        Map<String, Object> lfields2 = new HashMap<>();
        lfields2.put("x1", null);
        lfields2.put("x2", null);
        lfields2.put("x3", null);
        lfields2.put("x4", null);

        lfields.put("x1", lfields2);

        Timer lt = start();
        Map<String, Object> lresult = pickGraph.execute(lfields, LargePGO.class,null, PickGraphProperties.SNAKE_CASE);
        lresult = pickGraph.execute(lfields, LargePGO.class,null, PickGraphProperties.SNAKE_CASE);
        lresult = pickGraph.execute(lfields, LargePGO.class,null, PickGraphProperties.SNAKE_CASE);
        lt.stopAndPrint("time to execute large object");

        assertNotNull(lresult);
        assertEquals(2, lresult.get("x2"));
        assertEquals(3.0, lresult.get("x3"));
        assertEquals(4, lresult.get("x4"));
        assertEquals("new Object()", lresult.get("x5"));
        assertEquals("6", lresult.get("x6"));
        assertEquals("6", lresult.get("x7"));
        assertEquals("6", lresult.get("x8"));
        assertEquals("6", lresult.get("x9"));
        assertEquals("6", lresult.get("x10"));
        assertEquals("6", lresult.get("x11"));
        assertEquals("6", lresult.get("x12"));
        assertEquals("6", lresult.get("x13"));
        assertEquals("6", lresult.get("x14"));

        Map<String, Object> largeChildPGO = (Map<String, Object>) lresult.get("x1");
        assertEquals(true, largeChildPGO.get("x1"));
        assertEquals(BigDecimal.valueOf(2), largeChildPGO.get("x2"));
        assertEquals(3.0, largeChildPGO.get("x3"));
        assertEquals(4, largeChildPGO.get("x4"));
    }

    @Test
    void jsonSpeeds() throws JsonProcessingException {
        Gson gson = new GsonBuilder().setFieldNamingStrategy(field ->
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())
        ).create();

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

        LargePGO largePGO = new LargePGO();
        largePGO.setX1(largeChildPGO);
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


        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

        Timer t = start();
        for (int i = 0; i < 1000; i++) {
            String s = "string";
        }
        t.stopAndPrint("time");

        t = start();
        for (int i = 0; i < 1000; i++) {
            String s = gson.toJson(largePGO);
        }
        t.stopAndPrint("time");

        t = start();
        for (int i = 0; i < 1000; i++) {
            String s = mapper.writeValueAsString(largePGO);
        }
        t.stopAndPrint("time");
    }

    @Autowired
    private GraphQlSource graphql;
    @Test
    void graphql_speed() {
        String query = """
                {
                  largePGO {
                    x1 {
                        x1
                        x2
                        x3
                        x4
                        x5
                        x6
                        x7
                        x8
                        x9
                        x10
                        x11
                        x12
                        x13
                        x14
                    }
                    x2
                    x3
                    x4
                  }
                }
                """;
        GraphQL graphQl = graphql.graphQl();
        Timer t = start();
        ExecutionResult data = graphQl.execute(query);
        data = graphQl.execute(query);
        data = graphQl.execute(query);
        t.stopAndPrint("time to execute graphql large object");
    }


    @Test
    void testStuff() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        class d {
            public String x1 = "1";
            public BigDecimal x2 = BigDecimal.valueOf(1.5);
        }

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new d()));

    }
}
