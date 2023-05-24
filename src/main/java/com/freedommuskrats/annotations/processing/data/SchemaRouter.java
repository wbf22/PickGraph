package com.freedommuskrats.annotations.processing.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import com.freedommuskrats.config.PickGraphProperties;
import com.freedommuskrats.exception.PickGraphException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SchemaRouter {
    private final AnnotationProcessor annotationProcessor;
    private final ObjectMapper mapper;

    public SchemaRouter( AnnotationProcessor annotationProcessor, ObjectMapper mapper) {
        this.annotationProcessor = annotationProcessor;
        this.mapper = mapper;
    }

    @GetMapping(path = "${pick.graph.endpoint.path}/schema")
    public ResponseEntity<?> schemaEndpoint() {
        try {
            List<Endpoint> schema = annotationProcessor.getSchema();
            String resultJson = mapper.writeValueAsString(schema);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(resultJson, responseHeaders, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new PickGraphException("Failed to serialize PickGraph schema. May have encountered unknown object type." + "\n\n full exception: \n" + e);
        }
    }


}
