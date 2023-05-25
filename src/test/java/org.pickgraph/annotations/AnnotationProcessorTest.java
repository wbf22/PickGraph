package org.pickgraph.annotations;

import org.junit.jupiter.api.Test;
import org.pickgraph.annotations.processing.AnnotationProcessor;
import org.pickgraph.annotations.processing.data.ObjectData;
import org.pickgraph.annotations.processing.data.ObjectMapping;
import org.pickgraph.config.PickGraphAutoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = PickGraphAutoConfig.class)
class AnnotationProcessorTest {


    @Autowired
    private AnnotationProcessor annotationProcessor;


    @Test
    void test_annotationProcessor_setup() {
        Map<Class<?>, ObjectData> objData = annotationProcessor.getObjectDatas();
        Optional<Map.Entry<Class<?>, ObjectData>> dataEntry = objData.entrySet().stream().filter(entry -> entry.getValue().getName().equals("org.pickgraph.annotations.testObjects.TestPGO1")).findFirst();
        ObjectData testObjData = dataEntry.map(Map.Entry::getValue).orElse(null);

        assertFalse(objData.isEmpty());
        assert testObjData != null;
        assertNotNull(testObjData.getField("name"));
        assertNotNull(testObjData.getField("age"));

        Map<String, ObjectMapping> objMappings = annotationProcessor.getObjectMappings();
        ObjectMapping testObjMapping = objMappings.get("org.pickgraph.annotations.testObjects.TestPGO1");

        assertFalse(objMappings.isEmpty());
        assert testObjMapping != null;
        assertEquals("org.pickgraph.annotations.testObjects.TestPGO1", testObjMapping.getTargetClassName());
    }



}
