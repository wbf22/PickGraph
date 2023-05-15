package com.freedommuskrats.annotations.testObjects;

import com.freedommuskrats.annotations.PickGraphObject;

@PickGraphObject
public class TestPGO2 {
    private String name;
    private String lastName;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestPGO3 getTestPGO3() {
        return testPGO3;
    }

    public void setTestPGO3(TestPGO3 testPGO3) {
        this.testPGO3 = testPGO3;
    }
}
