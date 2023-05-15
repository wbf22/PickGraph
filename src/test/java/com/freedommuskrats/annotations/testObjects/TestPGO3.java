package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphObject;

import java.util.HashMap;
import java.util.Map;

@PickGraphObject
public class TestPGO3 {

    private Map<String, String> map = new HashMap<>();

    public TestPGO3() {
        map.put("key1", "value1");
        map.put("key2", "value2");
    }

    public TestPGO3(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
