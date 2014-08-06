package com.cantaa.util;

import java.lang.reflect.Field;

/**
 * @author Hans Lesmeister
 */
public class ReflectionUtil {

    public static void setFieldValue(Object target, String fieldName, Object value) {
        Field fld = findField(target.getClass(), fieldName, null);

        if (fld == null) {
            return;
        }

        if (!fld.isAccessible()) {
            fld.setAccessible(true);
        }

        try {
            fld.set(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name} and/or {@link Class type}. Searches all superclasses
     * up to {@link Object}.
     * <p>
     * (Borrowed from Spring)
     *
     * @param clazz the class to introspect
     * @param name the name of the field (may be {@code null} if type is specified)
     * @param type the type of the field (may be {@code null} if name is specified)
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Reject.ifNull(clazz, "Class must not be null");
        Reject.ifFalse(name != null || type != null, "Either name or type of the field must be specified");

        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }


}
