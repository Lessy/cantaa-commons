package com.cantaa.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility-Methods around Date and Time
 *
 * @author Hans Lesmeister
 */
public class DateUtil {

    /**
     * Get current date as timestamp
     *
     * @return timestamp
     */
    public static Timestamp createTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * Creates a date specified with the passed values
     *
     * @param year  Year
     * @param month Month (1-based, so 1 = Jan., 2 = Feb.)
     * @param day   Day of the month
     * @return Date-Instance
     */
    @SuppressWarnings("MagicConstant")
    public static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        resetTimePart(cal);
        return cal.getTime();
    }

    /**
     * Creates a date based on the actual date but without time-settings, so the
     * time part of this date is 00:00:00.000
     *
     * @return Date-Instance
     */
    public static Date createDate() {
        Calendar calendar = Calendar.getInstance();
        resetTimePart(calendar);
        return calendar.getTime();
    }

    /**
     * Sets the time part in a calendar to zero (Hours, Minutes, Seconds, Milliseconds)
     *
     * @param calendar Calendar
     * @throws NullPointerException if calendar is null
     */
    public static void resetTimePart(Calendar calendar) {
        setTimePart(calendar, 0, 0, 0, 0);
    }

    private static void setTimePart(Calendar calendar, int hours, int minutes, int seconds, int milliseconds) {
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);
    }

    /**
     * Sets the time part to the last second of the day. This is used for queries with something like
     * "date <= :someDate" and the date values in the table could have a time-part.
     *
     * @param date Date. If null the the actual date will be taken
     * @return date with the time-part set to 23:59:59 999
     */
    public static Date setTimeToEndOfDay(Date date) {
        Calendar calendar = getCalendar(date);
        setTimePart(calendar, 23, 59, 59, 999);
        return calendar.getTime();
    }

    /**
     * Sets the time part of a date to zero
     *
     * @param date Date
     * @return new Date with time part set to zero
     */
    public static Date resetTimePart(Date date) {
        Calendar calendar = getCalendar(date);
        resetTimePart(calendar);
        return calendar.getTime();
    }

    /**
     * Get a calendar
     *
     * @param date a date or null
     * @return calendar with the passe date set. If date is null then calendar represants the actual date
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();

        if (date != null) {
            cal.setTime(date);
        }

        return cal;
    }

    /**
     * Get a date for the last day of the month based on the applied date
     * @param date if null, then the current date is used
     * @return new date where the day is set to the last day of the month
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * Get a date for the first day of the month based on the applied date
     * @param date if null, then the current date is used
     * @return new date where the day is set to the first day of the month
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = getCalendar(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date addDays(Date date, int days) {
        return addValues(date, days, Calendar.DATE);
    }

    public static Date addMonths(Date date, int months) {
        return addValues(date, months, Calendar.MONTH);
    }

    public static Date addYears(Date date, int years) {
        return addValues(date, years, Calendar.YEAR);
    }

    private static Date addValues(Date date, int value, int type) {
        Calendar c = getCalendar(date);
        c.add(value, type);
        return c.getTime();
    }

    /**
     * Gets the Year-Part from a date
     *
     * @param date Date to extract the year from. If null, then the current year is returned
     * @return The year, extracted from the date
     */
    public static int getYear(Date date) {
        return getValue(date, Calendar.YEAR);
    }

    /**
     * Gets the Month-Part from a date
     *
     * @param date Date to extract the month from. If null, then the current month is returned
     * @return The month, extracted from the date, where Jan.=1, Feb=2, etc
     */
    public static int getMonth(Date date) {
        return getValue(date, Calendar.MONTH) + 1;
    }

    /**
     * Gets the Day in the month from a date
     *
     * @param date Date to extract the day from. If null, then the current day is returned
     * @return The day of the month, extracted from the date
     */
    public static int getDay(Date date) {
        return getValue(date, Calendar.DAY_OF_MONTH);
    }

    private static int getValue(Date date, int type) {
        Calendar c = getCalendar(date);
        return c.get(type);
    }


}
