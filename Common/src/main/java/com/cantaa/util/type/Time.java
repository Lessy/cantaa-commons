package com.cantaa.util.type;

import java.io.Serializable;
import java.util.Calendar;

import com.cantaa.util.Reject;

/**
 * The Time-Class encapsulates a time during the day. The time itself is stored as a long in Minutes since midnight
 * @author Hans Lesmeister
 */
public final class Time implements Serializable {

    private long longValue;
    private boolean simpleFormat = false;

    /**
     * Default Constructor
     */
    public Time() {
        this(0);
    }

    /**
     * Private so force using factory-methods
     * @param minutesSinceMidnight
     */
    private Time(long minutesSinceMidnight) {
        this.longValue = minutesSinceMidnight;
    }

    /**
     * @return the internally stored value
     */
    public Long getLongValue() {
        if (longValue < 0) {
            return null;
        }

        return Long.valueOf(longValue);
    }

    @Override
    public String toString() {
        return "Time{" +
                "timeOfDay=" + longValue +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Time time = (Time) o;

        if (longValue != time.longValue) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (longValue ^ (longValue >>> 32));
    }

    /**
     * @param time a Time-Object
     * @return null if time is null, otherwise the internal value
     */
    public static Long longValueOf(Time time) {
        if (time == null) {
            return null;
        }

        return time.getLongValue();
    }

    /**
     * @param calendar represents a time. If null, current time is taken
     * @return Time-Object
     */
    public static Time valueOf(Calendar calendar) {
        Calendar c = calendar;

        if (c == null) {
            c = Calendar.getInstance();
        }

        return Time.valueOf(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    /**
     * @param timeValue internal time value
     * @return Time-Object
     */
    public static Time valueOf(long timeValue) {
        return valueOf(Long.valueOf(timeValue));
    }

    /**
     * @param timeValue internal time value
     * @return Time-Object or null if the timeValue is null
     */
    public static Time valueOf(Long timeValue) {
        if (timeValue == null) {
            return null;
        }

        return new Time(timeValue);
    }

    /**
     * Create a time object
     * @param hours number of hours passed since midnight
     * @param minutes minutes to be added to the hours
     * @return Time-Object
     */
    public static Time valueOf(int hours, int minutes) {
        Reject.ifTrue((hours < 0) || (hours > 23), "hours not between 0 and 23");
        Reject.ifTrue((minutes < 0) || (minutes > 60), "minutes not between 0 and 59");
        return new Time((hours * 60) + minutes);
    }

    public int getHours() {
        Long value = getLongValue();
        if (value == null) {
            return -1;
        }

        int longValue = value.intValue();
        int m = longValue % 60;
        return (longValue - m) / 60;
    }

    public int getMinutes() {
        Long value = getLongValue();
        if (value == null) {
            return -1;
        }

        return value.intValue() % 60;
    }

    public boolean isSimpleFormat() {
        return simpleFormat;
    }

    public Time setSimpleFormat(boolean simpleFormat) {
        this.simpleFormat = simpleFormat;
        return this;
    }
}
