package com.freedommuskrats.annotations;

import com.freedommuskrats.config.PickGraphAutoConfig;
import com.freedommuskrats.annotations.processing.AnnotationProcessor;
import com.freedommuskrats.annotations.processing.data.ObjectData;
import com.freedommuskrats.annotations.processing.data.ObjectMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ContextConfiguration(classes = PickGraphAutoConfig.class)
class AnnotationProcessorTest {


    @Autowired
    private AnnotationProcessor annotationProcessor;


    @Test
    void test_annotationProcessor_setup() {
        annotationProcessor.setUp();
        List<ObjectData> objData = annotationProcessor.getObjectDatas();
        ObjectData testObjData = objData.stream().filter(obj -> obj.getName().equals("com.freedommuskrats.annotations.testObjects.TestPGO1")).findFirst().orElse(null);

        assertFalse(objData.isEmpty());
        assert testObjData != null;
        assertEquals("name", testObjData.getField("name").getName());
        assertEquals("age", testObjData.getField("age").getName());

        Map<String, ObjectMapping> objMappings = annotationProcessor.getObjectMappings();
        ObjectMapping testObjMapping = objMappings.get("com.freedommuskrats.annotations.testObjects.TestPGO1");

        assertFalse(objMappings.isEmpty());
        assert testObjMapping != null;
        assertEquals("com.freedommuskrats.annotations.testObjects.TestPGO1", testObjMapping.getTargetClassName());
    }

}
