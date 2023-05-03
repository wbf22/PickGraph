package com.freedommuskrats;

import com.freedommuskrats.testObjects.TestPickGraphObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, Object> result = pickGraph.execute(fields, TestPickGraphObject.class, Map.of("junk", new TestPickGraphObject()));

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

}
