package com.freedommuskrats.annotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.freedommuskrats.annotations.testObjects.TestPGO1;
import com.freedommuskrats.annotations.testObjects.TestPGO2;
import com.freedommuskrats.annotations.testObjects.TestPGO3;
import com.freedommuskrats.config.PickGraphAutoConfig;
import com.freedommuskrats.annotations.testObjects.TestEndpoint;
import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.freedommuskrats.PickGraph.convertMapCase;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {PickGraphAutoConfig.class, TestEndpoint.class})
class PickEndpointTest {

    @LocalServerPort
    private int port;

    @Test
    void endpointNormal() throws JsonProcessingException {
        Map<String, Object> fields = new HashMap<>();
        fields.put("age", null);

        Map<String, Object> fields2 = new HashMap<>();
        fields2.put("name", null);

        Map<String, Object> fields3 = Map.of("map", "");

        fields2.put("testPGO3", fields3);
        fields.put("testPGO2", fields2);


        WebClient webClient = WebClient.create();
        Mono<Map> mono = webClient.post()
                .uri("http://localhost:" + port + "/pickgraph")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(fields)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        StepVerifier.create(mono)
                .expectNextMatches(result -> {
                    assertFalse(result.containsKey("name"));
                    assertEquals(10, result.get("age"));

                    Map<String, Object> testPGO2 = (Map<String, Object>) result.get("testPGO2");
                    assertNotNull(testPGO2);
                    assertFalse(testPGO2.containsKey("description"));
                    assertEquals("jill", testPGO2.get("name"));

                    Map<String, Object> testPGO3 = (Map<String, Object>) testPGO2.get("testPGO3");
                    assertNotNull(testPGO3);
                    assertTrue(testPGO3.containsKey("map"));

                    return true;
                })
                .verifyComplete();

        String url = "http://localhost:" + port + "/pickgraph";
        RestTemplate template = new RestTemplate();

        Map res = template.postForEntity(url, fields, Map.class).getBody();

        Map result = res;
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
    void pickEndpointNormal() {

        Map<String, Object> fields = new HashMap<>();
        fields.put("age", null);

        Map<String, Object> fields2 = new HashMap<>();
        fields2.put("name", null);

        Map<String, Object> fields3 = Map.of("map", "");

        fields2.put("testPGO3", fields3);
        fields.put("testPGO2", fields2);

        WebClient webClient = WebClient.create();

        Mono<Map> responseMono = webClient.post()
                .uri("http://localhost:" + port + "/pick/TestPGO1") // set the endpoint path and any path variables
                .header("junk", "10")
                .contentType(MediaType.APPLICATION_JSON) // set the content type of the request body
                .body(BodyInserters.fromValue(fields)) // set the request body
                .retrieve() // switch to response mode
                .bodyToMono(Map.class); // convert the response body to a Mono<String>

        StepVerifier.create(responseMono)
                .expectNextMatches(result -> {
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

                    return true;
                })
                .verifyComplete();


        Mono<TestPGO1> resp2 = webClient.post()
                .uri("http://localhost:" + port + "/pick/TestPGO1") // set the endpoint path and any path variables
                .header("junk", "10")
                .contentType(MediaType.APPLICATION_JSON) // set the content type of the request body
                .body(BodyInserters.fromValue(fields)) // set the request body
                .retrieve() // switch to response mode
                .bodyToMono(TestPGO1.class); // convert the response body to a Mono<String>

        StepVerifier.create(resp2)
                .expectNextMatches(result -> {
                    assertNotNull(result);
                    assertNotNull(result.getTestPGO2());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void pickEndpoint_SnakeCase() {
        Map<String, Object> fields2 = new HashMap<>();
        fields2.put("name", null);

        Map<String, Object> fields3 = Map.of("map", "");

        fields2.put("testPGO3", fields3);

        String url = "http://localhost:" + port + "/pick/TestPGO2";
        RestTemplate template = new RestTemplate();

        long time = System.currentTimeMillis();
        Map res = template.postForEntity(url, fields2, Map.class).getBody();
        System.out.println("Total Time: " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        res = template.postForEntity(url, fields2, Map.class).getBody();
        System.out.println("Total Time: " + (System.currentTimeMillis() - time));

        Map testPGO2 = res;
        assertNotNull(testPGO2);
        assertFalse(testPGO2.containsKey("description"));
        assertEquals("jill", testPGO2.get("name"));

        Map<String, Object> testPGO3 = (Map<String, Object>) testPGO2.get("test_p_g_o3");
        assertNotNull(testPGO3);
        assertTrue(testPGO3.containsKey("map"));
    }





}
