package com.cantaa.util;

import com.cantaa.util.identify.Identifiable;

/**
 * @author Hans Lesmeister
 */
public class EqualsUtil {

    public static boolean equals(Object object, Object other) {
        return equals(object, other, false);
    }

    /**
     * Checks equality
     * @param object 1st object to compare
     * @param other 2nd object to compare
     * @param ignoreCase if true and both objects are string then they are compared with equalsIgnoreCase.
     *                   Default is false
     * @return true if:
     * <ul>
     *     <li>Both are null</li>
     *     <li>or both are Identifiable and both Id's are equal</li>
     *     <li>or standard equals() returns true</li>
     * </ul>
     */
    public static boolean equals(Object object, Object other, boolean ignoreCase) {
        if ((object == null) && (other == null)) {
            return true;
        }

        if ((object == null) || (other == null)) {
            return false;
        }

        if ((object instanceof Identifiable) && (other instanceof Identifiable)) {
            return equals(((Identifiable) object).getId(), ((Identifiable) other).getId());
        }

        if (ignoreCase && (object instanceof String) && (other instanceof String)) {
            return ((String) object).equalsIgnoreCase((String) other);
        }

        return object.equals(other);
    }


}
