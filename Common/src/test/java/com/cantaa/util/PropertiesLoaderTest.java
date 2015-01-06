package com.cantaa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class PropertiesLoaderTest {

    @Test
    public void testLoadPropertiesByList() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("com/cantaa/util/propertiesloadertest.properties");
        Properties properties = PropertiesLoader.loadProperties(list);
        assertNotNull(properties);
        assertEquals("testvalue", properties.getProperty("testkey"));
    }

    @Test
    public void testLoadPropertiesByArray() throws Exception {
        assertEquals(0, PropertiesLoader.loadProperties(new String[]{"blabla.properties"}).size());
        Properties properties = PropertiesLoader.loadProperties(new String[]{"com/cantaa/util/propertiesloadertest.properties"});
        assertNotNull(properties);
        assertEquals("testvalue", properties.getProperty("testkey"));
    }

    @Test
    public void testLoadWithClasspathPrefix() throws Exception {
        Properties properties = PropertiesLoader.loadProperties(new String[]{"classpath:com/cantaa/util/propertiesloadertest.properties"});
        assertNotNull(properties);
        assertEquals("testvalue", properties.getProperty("testkey"));
    }

    @Test
    public void testStripPrefix() throws Exception {
        Properties properties = PropertiesLoader.loadProperties(new String[]{"com/cantaa/util/propertiesloadertest.properties"});
        assertNotNull(properties);

        assertEquals("1", properties.getProperty("mypref.stripvalue"));
        assertEquals("2", properties.getProperty("mypref_stripvalue"));

        Properties strippedProperties = PropertiesLoader.stripProperties("mypref", properties, false);
        assertEquals("1", properties.getProperty("mypref.stripvalue"));
        assertEquals("2", properties.getProperty("mypref_stripvalue"));
        assertEquals(null, strippedProperties.getProperty("mypref.stripvalue"));
        assertEquals(null, strippedProperties.getProperty("mypref_stripvalue"));
        assertEquals("1", strippedProperties.getProperty("stripvalue"));

        strippedProperties = PropertiesLoader.stripProperties("mypref", properties, true, "_");
        assertEquals("1", properties.getProperty("mypref.stripvalue"));
        assertEquals(null, properties.getProperty("mypref_stripvalue"));
        assertEquals("2", properties.getProperty("stripvalue"));
        assertEquals(null, strippedProperties.getProperty("mypref.stripvalue"));
        assertEquals(null, strippedProperties.getProperty("mypref_stripvalue"));
        assertEquals("2", strippedProperties.getProperty("stripvalue"));


    }


}
