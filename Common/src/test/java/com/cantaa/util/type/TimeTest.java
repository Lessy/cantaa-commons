package com.cantaa.util.type;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class TimeTest {

    @Test
    public void testTime() throws Exception {
        Time time = Time.valueOf(0L);
        assertEquals(Long.valueOf(0L), time.getLongValue());
        assertEquals(null, Time.valueOf((Long) null));

        time = Time.valueOf(630L);
        assertEquals(630L, time.getLongValue().longValue());

    }

    @Test
    public void testTimeHours() throws Exception {
        Time time = Time.valueOf(10, 45);
        assertEquals(Long.valueOf(645L), time.getLongValue());

        time = Time.valueOf(0, 0);
        assertEquals(0L, time.getLongValue().longValue());

    }

    @Test
    public void testTimeGetHours() throws Exception {
        Time time = Time.valueOf(10, 45);
        assertEquals(10, time.getHours());
        assertEquals(45, time.getMinutes());
    }
}
