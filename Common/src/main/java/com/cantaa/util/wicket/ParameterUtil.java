package com.cantaa.util.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

/**
 * @author Hans Lesmeister
 * @author Andreas Micklei
 */
public class ParameterUtil {

    public static String getString(PageParameters parameters, String key, String defaultValue) {
        if (parameters == null) {
            return defaultValue;
        }

        StringValue value = parameters.get(key);
        if (value.isEmpty()) {
            return defaultValue;
        }

        return value.toString();
    }

    public static Long getLong(PageParameters parameters, String key, Long defaultValue) {
        if (parameters == null) {
            return defaultValue;
        }

        Long id;
        try {
            id = parameters.get(key).toLong();
            if (id == null) {
                id = defaultValue;
            }
        } catch (StringValueConversionException e) {
            id = defaultValue;
        }

        return id;
    }

//    public static <T> T getJSON(PageParameters parameters, String key, T defaultValue, Class<T> clazz) {
//        if (parameters == null) {
//            return defaultValue;
//        }
//
//        StringValue value = parameters.get(key);
//        if (value.isEmpty()) {
//            return defaultValue;
//        }
//
//        Gson gson = new Gson();
//        return gson.fromJson(value.toString(), clazz);
//    }

}
