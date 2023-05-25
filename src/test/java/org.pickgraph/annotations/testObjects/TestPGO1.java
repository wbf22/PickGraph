package org.pickgraph.annotations.testObjects;

import org.pickgraph.annotations.PickGraphObject;

@PickGraphObject
public class TestPGO1 {
    private String name;
    private Integer age;
    private TestPGO2 testPGO2;

    public TestPGO1() {
    }

    public TestPGO1(String name, Integer age, TestPGO2 testPGO2) {
        this.name = name;
        this.age = age;
        this.testPGO2 = testPGO2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public TestPGO2 getTestPGO2() {
        return testPGO2;
    }

    public void setTestPGO2(TestPGO2 testPGO2) {
        this.testPGO2 = testPGO2;
    }
}
