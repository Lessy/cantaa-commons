package com.cantaa.util;

import java.io.File;
import java.io.FileInputStream;
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

    /**
     * Read additional properties from an external file and use the default property name
     * @param properties input properties
     * @see #extendPropertiesFromExternalFile(Properties, String)
     */
    public static void extendPropertiesFromExternalFile(Properties properties) {
        extendPropertiesFromExternalFile(properties, null);
    }

    /**
     * Get a filename from the applied properties. If such a filename is provided and the file exists and is readable,
     * then read properties from that file and merge them into the applied properties
     * @param properties input properties
     * @param key name of the property to read the filename from. If omitted (null or empty) then the key will be
     *            <code>"system.extendedproperties"</code>
     *
     */
    public static void extendPropertiesFromExternalFile(Properties properties, String key) {
        if (properties == null) {
            log.debug("'properties' is null");
            return;
        }

        if (StringUtil.isEmpty(key)) {
            key = "system.extendedproperties";
        }

        String fileName = properties.getProperty(key);
        if (StringUtil.isEmpty(fileName)) {
            log.debug("No property '{}'", key);
            return;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            log.debug("File '{}' does not exist", file);
            return;
        }

        if (!file.canRead()) {
            log.debug("File '{}' cannot be read", file);
            return;
        }

        InputStream in = null;
        try {
            log.debug("Properties merged from file '{}'", file);
            in = new FileInputStream(file);
            Properties extendedProperties = new Properties();
            extendedProperties.load(in);
            properties.putAll(extendedProperties);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }
}
