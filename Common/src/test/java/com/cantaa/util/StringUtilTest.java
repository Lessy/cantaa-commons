package com.cantaa.util;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;

/**
 * Tests around StringUtil
 *
 * @author Hans Lesmeister
 */
public class StringUtilTest extends TestCase {

    public void testToUpperLower() {
        assertEquals(null, StringUtil.toUpperLower(null));
        assertEquals("", StringUtil.toUpperLower(""));
        assertEquals("A", StringUtil.toUpperLower("A"));
        assertEquals("A", StringUtil.toUpperLower("a"));
        assertEquals("Abc", StringUtil.toUpperLower("abc"));
        assertEquals("Abc", StringUtil.toUpperLower("ABC"));
        assertEquals("Abc", StringUtil.toUpperLower("aBc"));
        assertEquals("Abc", StringUtil.toUpperLower("AbC"));
    }

    /**
     * Test for StringUtil.trim()
     */
    public void testTrim() {
        assertNull(StringUtil.trim(null));
        assertEquals("", StringUtil.trim(""));
        assertEquals("", StringUtil.trim("   "));
        assertEquals("Test", StringUtil.trim("Test   "));
    }

    public void testJoin() {
        String a, b, c;
        a = "a";
        b = "b";
        c = "c";
        assertEquals("abc", StringUtil.join(null, new Object[]{a, b, c}).toString());

        b = null;
        assertEquals("ac", StringUtil.join(null, new Object[]{a, b, c}).toString());
    }

    public void testJoinAnyType() {
        String a = "a";
        String b = "b";
        Integer c = 1;
        Boolean d = true;
        List<?> list = Arrays.asList(a, b, c, d);
        assertEquals("ab1true", StringUtil.join(list, null));
    }

    public void testJoinNotEmpty() {
        String a, b;
        a = null;
        b = "2";
        assertEquals("2", StringUtil.join(null, new Object[]{a, b}));

        a = null;
        b = null;
        assertEquals("", StringUtil.join(null, new Object[]{a, b}));

        a = "1";
        b = "2";
        assertEquals("1 2", StringUtil.join(" ", new Object[]{a, b}));

        a = "1";
        b = null;
        assertEquals("1", StringUtil.join(null, new Object[]{a, b}));

        a = "1";
        b = "2";
        assertEquals("1;2", StringUtil.join(";", new Object[]{a, b}));

    }

    public void testObjectToString() {
        String s1 = null;
        String s2 = "blabla";
        assertNull(StringUtil.objectToString(s1));
        assertEquals("blabla", StringUtil.objectToString(s2));

        Integer i1 = null;
        Integer i2 = new Integer(42);
        assertNull(StringUtil.objectToString(i1));
        assertEquals("42", StringUtil.objectToString(i2));

        String s3 = null;
        String s4 = null;
        assertNull(StringUtil.objectToString(s3, null));
        assertEquals("blabla", StringUtil.objectToString(s4, "blabla"));

    }
}
