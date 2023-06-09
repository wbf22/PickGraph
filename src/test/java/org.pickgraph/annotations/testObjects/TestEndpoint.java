package org.pickgraph.annotations.testObjects;

import org.pickgraph.PickGraph;
import org.pickgraph.annotations.Schema.IncludeInPickGraphSchema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class TestEndpoint {

    private final PickGraph pickGraph;

    public TestEndpoint(PickGraph pickGraph) {
        this.pickGraph = pickGraph;
    }

    @PostMapping(path = "/pickgraph")
    @IncludeInPickGraphSchema(returnPickGraphObject = TestPGO1.class)
    public Map getTestObj(
            @RequestBody Map body) {
        return pickGraph.fulfillRequest(body, TestPGO1.class, Map.of("junk", 10));
    }
}
