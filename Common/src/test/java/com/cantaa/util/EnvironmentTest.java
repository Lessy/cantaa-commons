package com.cantaa.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EnvironmentTest {


    @Test
    public void testEnvironment() throws Exception {
        assertTrue(Environment.isApplicationWithType(null));
        assertFalse(Environment.isApplicationWithType("abc"));

        Environment.setApplicationType("abc");
        assertFalse(Environment.isApplicationWithType(null));
        assertFalse(Environment.isApplicationWithType("xxx"));
        assertTrue(Environment.isApplicationWithType("abc"));
    }
}