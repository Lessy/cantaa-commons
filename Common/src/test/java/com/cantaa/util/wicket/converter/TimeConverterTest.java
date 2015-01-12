package com.cantaa.util.wicket.converter;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.cantaa.util.type.Time;

/**
 * @author Hans Lesmeister
 */
public class TimeConverterTest {
    @Test
    public void testConvertToObject() throws Exception {
        TimeConverter converter = new TimeConverter();

        assertEquals(null, converter.convertToObject(null, null));

        assertEquals(645, convertTimeToLong("10:45"));
        assertEquals(1439, convertTimeToLong("23:59"));
        assertEquals(0, convertTimeToLong("00:00"));
        assertEquals(315, convertTimeToLong("05:15"));
    }

    private long convertTimeToLong(String stringTime) {
        TimeConverter converter = new TimeConverter();
        Time time = converter.convertToObject(stringTime, null);
        return time.getLongValue().longValue();
    }

    @Test
    public void testConvertToString() throws Exception {
        TimeConverter converter = new TimeConverter();
        assertEquals("10:45", converter.convertToString(Time.valueOf(645L), null));
        assertEquals("23:59", converter.convertToString(Time.valueOf(1439L), null));
        assertEquals("00:00", converter.convertToString(Time.valueOf(0L), null));
        assertEquals("05:15", converter.convertToString(Time.valueOf(315L), null));
    }

    @Test
    public void testConvertToStringSimple() throws Exception {
        TimeConverter converter = new TimeConverter();
        assertEquals("10:45", converter.convertToString(Time.valueOf(645L).setSimpleFormat(true), null));
        assertEquals("23:59", converter.convertToString(Time.valueOf(1439L).setSimpleFormat(true), null));
        assertEquals("0", converter.convertToString(Time.valueOf(0L).setSimpleFormat(true), null));
        assertEquals("05:15", converter.convertToString(Time.valueOf(315L).setSimpleFormat(true), null));
        assertEquals("5", converter.convertToString(Time.valueOf(5, 0).setSimpleFormat(true), null));
    }

    @Test
    public void testWrongInput() throws Exception {
//        assertEquals(600, convertTimeToLong("10"));
    }
}
