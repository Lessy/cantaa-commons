package com.cantaa.util.wicket;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cantaa.util.StringUtil;

/**
 * To create type safe bean string-paths and wicket Property-Models
 * @author Hans Lesmeister
 */
public class SafeModel {
    private static final Logger log = LoggerFactory.getLogger(SafeModel.class);

    private static final ThreadLocal<List<String>> PATH = new ThreadLocal<List<String>>();
    private static final ThreadLocal<Object> TARGET = new ThreadLocal<Object>();
    private static final ThreadLocal<Class<?>> LASTTYPE = new ThreadLocal<Class<?>>();

    private static final Map<Class<?>, Object> typeMap;


    private static final NullType nullType = new NullType();

    private static final class NullType {
    }

    static {
        typeMap = new HashMap<Class<?>, Object>();
        typeMap.put(boolean.class, Boolean.FALSE);
        typeMap.put(int.class, Integer.valueOf(0));
        typeMap.put(long.class, Long.valueOf(0));
        typeMap.put(BigDecimal.class, nullType);
        typeMap.put(Timestamp.class, nullType);
        typeMap.put(List.class, nullType);
    }


    @SuppressWarnings("unchecked")
    public static <T> T from(T object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }

        setTarget(object);
        return (T) from(object.getClass());
    }

    public static <T> T from(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }

        T proxy = createProxy(clazz);
        return proxy;
    }

    public static <T, K> IModel<T> model(@SuppressWarnings("UnusedParameters") K proxy) {
        PropertyModel<T> propertyModel = new PropertyModel<T>(TARGET.get(), buildPath());
        clearPath();
        return propertyModel;
    }

    public static <T, K> IModel<K> model(IModel<T> model, @SuppressWarnings("UnusedParameters") K proxy) {
        PropertyModel<K> propertyModel = new PropertyModel<K>(model, buildPath());
        clearPath();
        return propertyModel;
    }

    public static String path(@SuppressWarnings("UnusedParameters") Object proxy) {
        String path = buildPath();
        clearPath();
        return path;
    }

    private static <T> void setLastType(Class<T> lastType) {
        LASTTYPE.set(lastType);
    }

    private static <T> void setTarget(T object) {
        TARGET.set(object);
    }

    private static void clearPath() {
        List<String> list = PATH.get();
        if (list != null) {
            list.clear();
        }
        PATH.remove();
    }

    private static void addToPath(String propName) {
        List<String> list = PATH.get();
        if (list == null) {
            list = new ArrayList<String>();
            PATH.set(list);
        }

        list.add(propName);
    }

    @SuppressWarnings("unchecked")
    private static <T> T createProxy(Class<T> clazz) {
        ProxyFactory factory = new ProxyFactory();
        if (clazz.isInterface()) {
            factory.setInterfaces(new Class[] {clazz});
        }
        else {
            factory.setSuperclass(clazz);
        }

        factory.setFilter(
                new MethodFilter() {
                    @Override
                    public boolean isHandled(Method method) {
                        String methodName = method.getName();

                        if (methodName.startsWith("get")) {
                            return true;
                        }

                        if (methodName.startsWith("is")) {
                            return true;
                        }

                        return false;
                    }
                }
        );//      return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, HANDLER);

        MethodHandler handler = new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                String methodName = thisMethod.getName();
                int pos = methodName.startsWith("get") ? 3 : 2; // getXXX or isXXX
                String propName = StringUtil.lowerFirstCharacter(methodName.substring(pos));
                addToPath(propName);
                Class<T> methodType = (Class<T>) thisMethod.getReturnType();
                setLastType(methodType);

                if (methodType.isPrimitive()) {
                    Object retVal = mapType(methodType);
                    if (retVal == null) {
                        throw new RuntimeException("Primitive type " + methodType + " is not supported");
                    }
                    return retVal;
                }

                if (Modifier.isFinal(methodType.getModifiers())) {
                    return null;
                }

                log.debug("MethodType to analyse: {}", methodType);
                Object retVal = mapType(methodType);
                if (retVal == null) {
                    log.debug("Mapping returned null, so create proxy");
                    return createProxy(methodType);
                }

                if (retVal instanceof NullType) {
                    log.debug("Mapping returned NullType");
                    return null;
                }
                else {
                    log.debug("Mapping returned: {}", retVal);
                    return retVal;
                }

            }
        };

        try {
            return (T) factory.create(new Class<?>[0], new Object[0], handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Object mapType(Class<T> type) {
        return typeMap.get(type);
    }

    private static String buildPath() {
        List<String> list = PATH.get();
        if (list == null) {
            throw new IllegalStateException("No current path available");
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);

            if (i > 0) {
                b.append(".");
            }

            b.append(s);
        }

        return b.toString();
    }
}
