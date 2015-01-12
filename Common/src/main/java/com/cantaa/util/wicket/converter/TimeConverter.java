package com.cantaa.util.wicket.converter;

import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;

import com.cantaa.util.StringUtil;
import com.cantaa.util.type.Time;

/**
 * Wicket-Converter for the Time-Type
 *
 * @author Hans Lesmeister
 */
public class TimeConverter extends AbstractConverter<Time> {
    @Override
    public Time convertToObject(String value, Locale locale) {

        if (StringUtil.isEmpty(value)) {
            return null;
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        Format timeFormat = getTimeFormat(locale);
        return parse(timeFormat, value, locale);
    }

    private SimpleDateFormat getTimeFormat(Locale locale) {
        return new TimeFormat(locale);
    }

    @Override
    public String convertToString(Time value, Locale locale) {
        if (value == null) {
            return null;
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        Calendar cal = Calendar.getInstance(locale);
        int h = value.getHours();
        int m = value.getMinutes();

        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, m);

        if (value.isSimpleFormat() && (m == 0)) {
           return String.valueOf(h);
        }

        return getTimeFormat(locale).format(cal.getTime());
    }

    @Override
    protected Class<Time> getTargetType() {
        return Time.class;
    }

    private static class TimeFormat extends SimpleDateFormat {
        TimeFormat(Locale locale) {
            super("HH:mm", locale);
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            Date date = (Date) super.parseObject(source, pos);

            if (date == null) {
                return null;
            }

            Calendar c = getCalendar();
            c.setTime(date);

            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);

            return Time.valueOf(Long.valueOf((h * 60) + m));
        }

    }

}
