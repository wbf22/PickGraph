package com.freedommuskrats.annotations;

import com.freedommuskrats.annotations.processing.data.Endpoint;
import com.freedommuskrats.annotations.testObjects.TestEndpoint;
import com.freedommuskrats.config.PickGraphAutoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {PickGraphAutoConfig.class, TestEndpoint.class})
class SchemaEndpointTest {

    @LocalServerPort
    private int port;

    @Test
    void schemaEndpointTest() {
        String url = "http://localhost:" + port + "/pick/schema";
        RestTemplate template = new RestTemplate();

        List<Map<String, Object>> result = template.getForEntity(url, List.class).getBody();

        assertNotNull(result);
        Map first = result.get(0);
        assertEquals("/pick/TestPGO2", first.get("path"));
        assertEquals("TestPGO2", first.get("requestTypeName"));

        Map second = result.get(1);
        assertEquals("/pick/LargePGO", second.get("path"));
        assertEquals("LargePGO", second.get("requestTypeName"));
        Map largePGO = (Map) second.get("defaultObject");
        Map largeChildPGO = (Map) largePGO.get("x1");
        assertType(largeChildPGO.get("x1"), Boolean.class);
        assertEquals(false, largeChildPGO.get("x1"));

        assertType(largeChildPGO.get("x2"), Integer.class);
        assertEquals(0, largeChildPGO.get("x2"));

        assertType(largeChildPGO.get("x3"), Double.class);
        assertEquals(0.0, largeChildPGO.get("x3"));

        assertType(largeChildPGO.get("x4"), Integer.class);
        assertEquals(0, largeChildPGO.get("x4"));

        assertType(largeChildPGO.get("x5"), String.class);
        assertEquals("", largeChildPGO.get("x5"));

        assertEquals(18, largeChildPGO.size());

        assertType(largePGO.get("x2"), Integer.class);
        assertEquals(0, largePGO.get("x2"));

        assertType(largePGO.get("x3"), Double.class);
        assertEquals(0.0, largePGO.get("x3"));

        assertType(largePGO.get("x4"), Integer.class);
        assertEquals(0, largePGO.get("x4"));

        assertType(largePGO.get("x5"), String.class);
        assertEquals("", largePGO.get("x5"));

        assertEquals(18, largePGO.size());


        Map third = result.get(2);
        assertEquals("/pick/TestPGO1", third.get("path"));
        assertEquals("TestPGO1", third.get("requestTypeName"));

        Map testPGO1 = (Map) third.get("defaultObject");
        assertType(testPGO1.get("name"), String.class);
        assertEquals("", testPGO1.get("name"));

        assertType(testPGO1.get("age"), Integer.class);
        assertEquals(0, testPGO1.get("age"));

        Map testPGO2 = (Map) testPGO1.get("testPGO2");
        assertType(testPGO2.get("name"), String.class);
        assertEquals("", testPGO2.get("name"));

        assertType(testPGO2.get("lastName"), String.class);
        assertEquals("", testPGO2.get("lastName"));

        assertType(testPGO2.get("description"), String.class);
        assertEquals("", testPGO2.get("description"));

        Map testPGO3 = (Map) testPGO2.get("testPGO3");
        assertType(testPGO3.get("map"), Map.class);

        Map map = (Map) testPGO3.get("map");
        assertEquals("", map.get(""));


        Map fourth = result.get(3);
        assertEquals("/pickgraph", fourth.get("path"));
        assertEquals("TestPGO1", fourth.get("requestTypeName"));


    }

    private void assertType(Object x2, Class<?> type) {
        assertTrue(type.isAssignableFrom(x2.getClass()));
    }

}
