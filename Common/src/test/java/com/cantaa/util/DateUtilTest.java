package com.cantaa.util;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class DateUtilTest {

    @Test
    public void testGetMonth() throws Exception {
        Date date = DateUtil.createDate(2015, 11, 2);
        int month = DateUtil.getMonth(date);
        assertEquals(11, month);
    }

    @Test
    public void testGetDay() throws Exception {
        Date date = DateUtil.createDate(2015, 11, 2);
        int day = DateUtil.getDay(date);
        assertEquals(2, day);
    }

    @Test
    public void testGetFirstDayOfMonth() throws Exception {
        Date date = DateUtil.createDate(2015, 11, 2);
        Date newDate = DateUtil.getFirstDayOfMonth(date);
        assertEquals(DateUtil.createDate(2015, 11, 1), newDate);
    }

    @Test
    public void testGetLastDayOfMonth() throws Exception {
        Date date = DateUtil.createDate(2015, 11, 2);
        Date newDate = DateUtil.getLastDayOfMonth(date);
        assertEquals(DateUtil.createDate(2015, 11, 30), newDate);
    }
}