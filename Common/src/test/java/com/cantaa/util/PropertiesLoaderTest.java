package com.cantaa.util;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class PropertiesLoaderTest {

    @Test
    public void testLoadProperties() throws Exception {
        assertEquals(0, PropertiesLoader.loadProperties(new String[]{"blabla.properties"}).size());
        Properties properties = PropertiesLoader.loadProperties(new String[]{"com/cantaa/util/propertiesloadertest.properties"});
        assertNotNull(properties);
        assertEquals("testvalue", properties.getProperty("testkey"));
    }
}
