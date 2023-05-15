package com.freedommuskrats.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommuskrats.PickGraph;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import com.freedommuskrats.annotations.processing.data.UserEndpoint;
import com.freedommuskrats.exception.PickGraphException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PickGraphSchemaEndpoint {
    private PickGraph pickGraph;
    private AnnotationProcessor annotationProcessor;
    private PickGraphProperties properties;

    public PickGraphSchemaEndpoint(PickGraph pickGraph, AnnotationProcessor annotationProcessor, PickGraphProperties properties) {
        this.pickGraph = pickGraph;
        this.annotationProcessor = annotationProcessor;
        this.properties = properties;
    }

    @GetMapping(path = "${pick.graph.endpoint-path}/schema")
    public ResponseEntity<?> schemaEndpoint() {
//        try {
//
//            annotationProcessor.getSchema();
//            String resultJson = mapper.writeValueAsString(result);
//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//            return new ResponseEntity<>(resultJson, responseHeaders, HttpStatus.OK);
//        } catch (JsonProcessingException e) {
//            throw new PickGraphException("Failed to serialize result from PickGraph call for object: \n\n"
//                    + result + "\n\n full exception: \n" + e);
//        }
        return null;
    }


    private Map prepareArgs(Map headers, Map params) {
        Map args = new HashMap();
        args.putAll(headers);
        args.putAll(params);
        return args;
    }

}

