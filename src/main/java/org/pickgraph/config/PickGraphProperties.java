package org.pickgraph.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;
import org.pickgraph.util.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.pickgraph.util.JsonFormat.*;

@Component
public class PickGraphProperties {

    @Value("${pick.graph.json-format}")
    private JsonFormat jsonFormat;

    @Value("${pick.graph.playground}")
    private boolean playground;

    @Value("${pick.graph.endpoint.path}")
    private String endpointPath;


    public String getEndpointPath() {
        return endpointPath;
    }

    public JsonFormat getJsonFormat() {
        return jsonFormat;
    }

    public boolean isPlayground() {
        return playground;
    }


    public PropertyNamingStrategy getStrategyOrProjectDefault(JsonFormat format) {
        if (format == null || format.equals(NOT_SPECIFIED)) {
            return getStrategy(jsonFormat);
        } else {
            return getStrategy(format);
        }
    }

    public CaseFormat getCaseFormatOrProjectDefault(JsonFormat format) {
        if (format == null || format.equals(NOT_SPECIFIED)) {
            return getCaseFormat(jsonFormat);
        } else {
            return getCaseFormat(format);
        }
    }
}