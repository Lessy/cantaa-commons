package com.cantaa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this class to determine in which Application we currenty are. For instance: the Import-Scheduler must only
 * run in the MBAK-Application and not in the Admin-Application. in Wicket-Applications, the type is determined by
 * AbstractApplicationFactory. In other Applications, the Method <code>setApplicationType(...)</code> can be called at
 * program start directly
 *
 * @author Hans Lesmeister
 */
public final class Environment {
    private static final Logger log = LoggerFactory.getLogger(Environment.class);

    private static volatile String applicationType = null;
    private static final Object synchronizedObject = new Object();

    public static void setApplicationType(String newApplicationType) {
        synchronized(synchronizedObject) {
            applicationType = newApplicationType;
        }
    }

    public static boolean isApplicationWithType(String type) {
        String s = applicationType;
        log.info("isApplicationWithType[type={}, systemProp={}]", type, s);

        if (type == null) {
            return (s == null);
        }

        return type.equals(s);
    }

}
