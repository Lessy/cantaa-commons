package com.cantaa.util;

import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * @author Hans Lesmeister
 */
public class ReflectionUtilTest {
    private String s1;

    @Test
    public void testSetFieldValue() {
        assertNull(s1);
        ReflectionUtil.setFieldValue(this, "s1", "Hi");
        assertEquals("Hi", s1);
    }
}
