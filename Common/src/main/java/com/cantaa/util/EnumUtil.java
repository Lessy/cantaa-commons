package com.cantaa.util;

/**
 * @author Hans Lesmeister
 */
public class EnumUtil {

    /**
     * Gets the Object holding the value
     * @param enumValues array of Objects (usually Enum's)
     * @param value The value to lookup
     * @param <T> Type of the object
     * @return Object-instance, Enum or null if value is null or if value cannot be found
     */
    public static <T extends ValueHolder> T valueOf(T[] enumValues, Integer value) {
        if (value == null) {
            return null;
        }

        int intValue = value.intValue();

        for (T enumValue : enumValues) {
            if (enumValue.getValue() == intValue) {
                return enumValue;
            }
        }

        return null;
    }
}
