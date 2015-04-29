package com.cantaa.util.spring;

import java.util.Properties;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.cantaa.util.Environment;

/**
 * @author Hans Lesmeister
 */
public class CantaaPropertyPlaceholderConfigurerTest {

    @After
    public void tearDown() throws Exception {
        Environment.setApplicationType(null);
    }

    @Test
    public void testStripPropertiesPrefix() throws Exception {
        Environment.setApplicationType("pref");
        Properties p = new Properties();
        p.setProperty("pref.n1", "bla");
        p.setProperty("praf.n1", "blabla");
        CantaaPropertyPlaceholderConfigurer.stripProperties(p);
        assertEquals("bla", p.getProperty("n1"));
        assertEquals("blabla", p.getProperty("praf.n1"));
    }
}
