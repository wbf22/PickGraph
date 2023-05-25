package org.pickgraph.annotations.processing.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pickgraph.PickGraph;
import org.pickgraph.annotations.processing.AnnotationProcessor;
import org.pickgraph.config.PickGraphProperties;
import org.pickgraph.exception.PickGraphException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EndpointRouter {

    private PickGraph pickGraph;
    private AnnotationProcessor annotationProcessor;
    private PickGraphProperties properties;

    public EndpointRouter(PickGraph pickGraph, AnnotationProcessor annotationProcessor, PickGraphProperties properties) {
        this.pickGraph = pickGraph;
        this.annotationProcessor = annotationProcessor;
        this.properties = properties;
    }

    @PostMapping(path = "${pick.graph.endpoint.path}/{requestType}")
    public ResponseEntity<?> userEndpoint(
            @RequestBody Map body,
            @RequestHeader Map headers,
            @RequestParam Map params,
            @PathVariable String requestType
    ) {
        UserEndpoint userEndpoint = annotationProcessor.getUserEndpoints().get(requestType);
        if (userEndpoint == null) {
            throw new PickGraphException("No @PickEndpoint found for object/name " + requestType);
        }
        Object result = pickGraph.fulfillRequest(
                body,
                userEndpoint.getRequestType(),
                prepareArgs(headers, params),
                userEndpoint.getJsonFormat()
        );

        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(properties.getStrategyOrProjectDefault(userEndpoint.getJsonFormat()));
        try {
            String resultJson = mapper.writeValueAsString(result);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(resultJson, responseHeaders, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new PickGraphException("Failed to serialize result from PickGraph call for object: \n\n"
                    + result + "\n\n full exception: \n" + e);
        }
    }


    private Map prepareArgs(Map headers, Map params) {
        Map args = new HashMap();
        args.putAll(headers);
        args.putAll(params);
        return args;
    }

}
