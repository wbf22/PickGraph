package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphObject;

@PickGraphObject
public class TestPickGraphObject {
    private String name;
    private Integer age;
    private TestPGO2 testPGO2;

    public TestPickGraphObject() {
    }

    public TestPickGraphObject(String name, Integer age, TestPGO2 testPGO2) {
        this.name = name;
        this.age = age;
        this.testPGO2 = testPGO2;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public TestPGO2 getTestPGO2() {
        return testPGO2;
    }
}
