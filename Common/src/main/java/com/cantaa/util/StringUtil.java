package com.cantaa.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * All kind of String-Utility Methods
 *
 * @author Hans Lesmeister
 */
public class StringUtil {

    /**
     * Trim with respect of Nullpointer
     *
     * @param value The value to be trimmed
     * @return Trimmed String or null if the passed String was null
     */
    public static String trim(String value) {
        return (value == null) ? null : value.trim();
    }

    public static CharSequence join(String separator, Object... values) {
        if (values == null) {
            return null;
        }

        return join(separator, Arrays.asList(values));
    }

    public static CharSequence join(String separator, List<Object> values) {
        if (values == null) {
            return null;
        }

        if (values.isEmpty()) {
            return null;
        }

        if (values.size() == 1) {
            return objectToString(values.get(0));
        }

        boolean added = false;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            String value = objectToString(values.get(i));
            if (StringUtil.isEmpty(value)) {
                continue;
            }

            if ((added) && (separator != null)) {
                b.append(separator);
            }

            b.append(value);
            added = true;
        }

        return b.toString();
    }

    /**
     * Concats an array of objects
     *
     * @param objects Objects
     * @return Concatenated
     * @deprecated Use join instead (returns CharSequence instead of StringBuilder since 17.07.2014)
     */
    public static CharSequence concatenate(Object... objects) {
        return join(null, objects);
    }

    /**
     * Concats 2 objects with an optional separator. Sep. is only inserted if objects are both not null
     *
     * @return Concatenated string
     * @param sep Separator, space if null
     * @deprecated Use join instead
     */
    public static String concatenateNotEmpty(String sep, String s1, String s2) {
        if (sep == null) {
            sep = " ";
        }

        CharSequence join = join(sep, s1, s2);
        if (join == null) {
            return null;
        }

        return join.toString();
    }

    /**
     * Concats 2 objects separated by a space. Sep. is only inserted if objects are both not null
     *
     * @return Concatenated string
     * @deprecated Use join instead
     */
    public static String concatenateNotEmpty(String s1, String s2) {
        return concatenateNotEmpty(null, s1, s2);
    }


    /**
     * Prueft ob uebergebene Zeichenkette null oder ist bzw nur aus white spaces
     * besteht.
     *
     * @return true, wenn Zeichenkette aus mindestens einem nocn-white-space Zeichen
     *         besteht, ansonsten false
     */
    public static boolean isEmpty(String s) {
        return ((s == null) || (s.trim().length() == 0));
    }

    /**
     * Gets the toString-Version of the passed object or null if the object is null
     *
     * @param obj Object
     * @return String-Representation or null
     */
    public static String objectToString(Object obj) {
        return objectToString(obj, null);
    }

    /**
     * Gets the toString-Version of the passed object or the passed default if the object is null
     *
     * @param obj      Object
     * @param defValue To be returned if obj is null
     * @return String-Representation or defValue
     */
    public static String objectToString(Object obj, String defValue) {
        if (obj == null) {
            return defValue;
        }

        return obj.toString();
    }

    /**
     * Converts a String to the Format "Xxxxxx".
     */
    public static String toUpperLower(String value) {

      if (value == null) {
         return null;
    }

      if (value.length() == 0) {
         return "";
      }

      if (value.length() == 1) {
         return value.toUpperCase();
      }

      return join(
              null,
              value.substring(0, 1).toUpperCase(),
              value.substring(1).toLowerCase()).toString();
   }

    /**
     * Outputs the throwable and its stacktrace to the stringbuffer. If stopAtWicketSerlvet is true
     * then the output will stop when the org.apache.wicket servlet is reached. sun.reflect.
     * packages are filtered out.
     * <p/>
     * Copied from Strings.outputThrowable from Wicket
     *
     * @param cause
     */
    public static String writeStackTrace(Throwable cause) {
        StringBuilder sb = new StringBuilder();
        sb.append(cause);
        sb.append("\n");
        StackTraceElement[] trace = cause.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            String traceString = trace[i].toString();
            if (!(traceString.startsWith("sun.reflect.") && i > 1)) {
                sb.append("     at ");
                sb.append(traceString);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Appends a file separator (File.separator) if not already there
     */
    public static String appendFileSeparatorIfNeeded(String dir) {
        return appendFileSeparatorIfNeeded(dir, File.separator);
    }

    /**
     * Appends a string at the end if it is not already there. Can especially be used to add a
     * trailing slash or file-separator to directory/url-names.
     *
     * @param dir
     * @param separator
     * @return dir with evt. separator appended or empty string if dir was null or empty
     */
    public static String appendFileSeparatorIfNeeded(String dir, String separator) {
        if (StringUtil.isEmpty(dir)) {
            return "";
        }

        if (!dir.endsWith(separator)) {
            dir += separator;
        }

        return dir;
    }

    public static String lowerFirstCharacter(String s) {
        if (isEmpty(s)) {
            return s;
        }

        String first = s.substring(0, 1).toLowerCase();
        String rest = s.substring(1);

        return first + rest;
    }

//    public static String beanToString(Object o) {
//        return ToStringBuilder.reflectionToString(o);
//    }

    public static String getLastCharacter(String s) {
        if (s == null) {
            return null;
        }

        int length = s.length();
        if (length == 0) {
            return "";
        }

        return String.valueOf(s.charAt(length - 1));
    }
}
