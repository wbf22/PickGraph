package com.freedommuskrats.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.freedommuskrats.PickGraph;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.freedommuskrats.config.PickGraphProperties.getStrategy;

@Configuration
@ComponentScan("com.freedommuskrats")
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
    public PickGraph pickGraph(AnnotationProcessor annotationProcessor) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(getStrategy(properties.getJsonFormat()));

        return new PickGraph(properties, annotationProcessor);
    }
}
