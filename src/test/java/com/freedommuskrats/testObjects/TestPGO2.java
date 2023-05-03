package com.freedommuskrats.testObjects;

import com.freedommuskrats.annotations.PickGraphObject;

@PickGraphObject
public class TestPGO2 {
    private String name;
    private String description;
    private TestPGO3 testPGO3;

    public TestPGO2() {

    }

    public TestPGO2(String name, TestPGO3 testPGO3) {
        this.name = name;
        this.testPGO3 = testPGO3;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TestPGO3 getTestPGO3() {
        return testPGO3;
    }
}