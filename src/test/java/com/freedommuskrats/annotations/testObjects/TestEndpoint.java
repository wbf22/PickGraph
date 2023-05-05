package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.PickGraph;
import com.freedommuskrats.annotations.PickEndpoint;
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
    public Map getTestObj(
            @RequestBody Map body) {
        return pickGraph.fulfillRequest(body, TestPickGraphObject.class, Map.of("junk", 10));
    }
}
