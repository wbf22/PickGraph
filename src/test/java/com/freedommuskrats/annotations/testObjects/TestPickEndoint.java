package com.freedommuskrats.annotations.testObjects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TestPickEndoint {

    @PostMapping(path = "/gh")
    Object pickEndpoint();
}
