package com.cantaa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.wicket.util.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load properties for different environments.
 * @author Hans Lesmeister
 */
public class PropertiesLoader {
    private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

    public static Properties loadProperties(String[] propResourceNames) {
        return loadProperties(Arrays.asList(propResourceNames));
    }

    public static Properties loadProperties(Iterable<String> propResourceNames) {

        Properties properties = new Properties();

        for (String propFile : propResourceNames) {
            loadPropertiesFromResources(propFile, properties);
        }

        return properties;
    }

    private static void loadPropertiesFromResources(String propFile, Properties targetProperties) {
        if (propFile.startsWith("classpath:")) {
            propFile = propFile.replace("classpath:", "");
        }

        InputStream in = PropertiesLoader.class.getResourceAsStream("/" + propFile);
        if (in == null) {
            // Properties-File does not exist. Ignore
            log.warn("Properties File {} does not exist.", propFile);
            return;
        }

        log.info("Loading properties from file: {}", propFile);
        loadPropertiesFromStream(in, targetProperties);
    }

    /**
     * Loads properties from the stream and adds them to the applied target properties.
     * The input stream is closed after reading the properties so the caller does not need to take care
     * about that.
     *
     * @param in               Stream to read from. May not be null, the caller must take care that the stream is not null
     * @param targetProperties
     */
    public static void loadPropertiesFromStream(InputStream in, Properties targetProperties) {
        try {
            Properties properties = new Properties();
            properties.load(in);
            targetProperties.putAll(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Write the contents of a properties instance to the log
     *
     * @param props Properties to log
     */
    public static void logProperties(Properties props) {
        log.info("*** Configured Properties ***");
        List<String> list = getPropertiesAsList(props);
        for (String s : list) {
            log.info(s);
        }
    }

    public static List<String> getPropertiesAsList(Properties properties) {
        List<String> list = new ArrayList<String>(properties.size());
        Enumeration en = properties.propertyNames();
        while (en.hasMoreElements()) {
            String property = (String) en.nextElement();
            String value = property.toLowerCase().contains("password") ? "*****" : properties.getProperty(property);
            list.add(property + " = " + value);
        }
        Collections.sort(list);

        return list;
    }

    public static Properties stripProperties(String prefix, Properties properties) {
        return stripProperties(prefix, properties, true, null);
    }

    public static Properties stripProperties(String prefix, Properties properties, boolean rewrite) {
        return stripProperties(prefix, properties, rewrite, null);
    }

    /**
     * Strips a prefix from properties
     * @param prefix Prefix to strip
     * @param sourceProps Properties to strip
     * @param rewrite if true (default) then properties with stripped names overwrite back to the source properties
     * @param separator is added to the prefix (Default is a dot.)
     * @return new Properties-instance with the stripped properties only
     * <pre><code>
     *     Properties props = new Properties();
     *     props.setProperty("check.uno", "Some Value");  // Separated by a dot
     *     stripProperties("check", props);               // Without the dot
     *     log.debug(prop.getProperty("uno"));            // ==> "Some Value"
     *     log.debug(prop.getProperty("check.uno"));      // ==> "Some Value" => Original prop with prefix is NOT removed
     * </code></pre>
     */
    public static Properties stripProperties(String prefix, Properties sourceProps, boolean rewrite, String separator) {
        if (StringUtil.isEmpty(prefix) || (sourceProps == null)) {
            return null;
        }

        String localPrefix = prefix + (separator == null ? ".": separator);
        Properties strippedProps = new Properties();
        Set<String> names = sourceProps.stringPropertyNames();
        for (String name : names) {
            if (name.startsWith(localPrefix)) {
                String value = sourceProps.getProperty(name);
                String newName = name.substring(localPrefix.length());
                strippedProps.setProperty(newName, value);

                if (rewrite) {
                    sourceProps.remove(name);
                    sourceProps.setProperty(newName, value);
                }
            }
        }

        return strippedProps;
    }

}
