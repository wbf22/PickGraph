package com.freedommuskrats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(PickGraphProperties.class)
public class PickGraphAutoConfig {
    private final PickGraphProperties properties;

    public PickGraphAutoConfig(PickGraphProperties properties) {
        this.properties = properties;
    }

    public PickGraphAutoConfig() {
        this.properties = new PickGraphProperties();
    }


    @Bean
    @ConditionalOnMissingBean
    public PickGraph myService() {
        ObjectMapper mapper = new ObjectMapper();
        if (properties.getJsonFormat().equals(PickGraphProperties.SNAKE_CASE)) {
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        } else {
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        }

        return new PickGraph(properties, mapper);
    }

    @Bean
    public AnnotationProcessor annotationProcessor() {
        return new AnnotationProcessor();
    }
}
