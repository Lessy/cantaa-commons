package com.cantaa.util.spring;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class CantaaPropertyPlaceholderConfigurerTest {

    @Test
    public void testStripPropertiesPrefix() throws Exception {
        Properties p = new Properties();
        p.setProperty("pref.n1", "bla");
        p.setProperty("praf.n1", "blabla");
        CantaaPropertyPlaceholderConfigurer.stripPropertiesPrefix("pref", p);
        assertEquals("bla", p.getProperty("n1"));
        assertEquals("blabla", p.getProperty("praf.n1"));
    }
}
