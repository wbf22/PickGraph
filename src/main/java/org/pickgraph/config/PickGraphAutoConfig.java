package org.pickgraph.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pickgraph.PickGraph;
import org.pickgraph.annotations.processing.AnnotationProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.pickgraph.util.JsonFormat.getStrategy;


@Configuration
@ComponentScan("org.pickgraph")
@PropertySource("classpath:application-pickgraph.properties")
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
