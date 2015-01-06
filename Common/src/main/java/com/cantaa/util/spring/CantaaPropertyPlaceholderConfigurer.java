package com.cantaa.util.spring;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.cantaa.util.PropertiesLoader;
import com.cantaa.util.StringUtil;

/**
 * Replacement Placeholder-Configurer to be used in Spring Context
 * @author Hans Lesmeister
 */
public class CantaaPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final Logger log = LoggerFactory.getLogger(CantaaPropertyPlaceholderConfigurer.class);
    public static final String APPLICATION_TYPE = "cantaa.commons.application.type";

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties props = super.mergeProperties();
        stripProperties(props);
        log.info("** Properties loaded from SpringContext **");
        PropertiesLoader.logProperties(props);
        return props;
    }

    /**
     * Check for the value of system property <code>CommonConstants.APPLICATION_TYPE</code> and strip the properties
     * with that value as prefix
     * @param props Properties to (eventually) strip
     */
    public static void stripProperties(Properties props) {
        String type = System.getProperty(APPLICATION_TYPE);

        if (!StringUtil.isEmpty(type)) {
            PropertiesLoader.stripProperties(type, props, true);
//            stripPropertiesPrefix(type, props);
        }
    }

//    /**
//     * Strips prefix (plus dot) from property names and sets the stripped property (name + value) back into the
//     * Properties-List
//     * <pre><code>
//     *     Properties props = new Properties();
//     *     props.setProperty("check.uno", "Some Value");  // Separated by a dot
//     *     stripProperties("check", props);               // Without the dot
//     *     log.debug(prop.getProperty("uno"));            // ==> "Some Value"
//     *     log.debug(prop.getProperty("check.uno"));      // ==> "Some Value" => Original prop with prefix is NOT removed
//     * </code></pre>
//     * @param prefix Prefix to check on
//     * @param props Properties to alter
//     */
//    public static void stripPropertiesPrefix(String prefix, Properties props) {
//        if (props == null) {
//            return;
//        }
//
//        if (StringUtil.isEmpty(prefix)) {
//            return;
//        }
//
//        String strip = prefix + ".";
//        Set<String> names = props.stringPropertyNames();
//        for (String name : names) {
//            if (name.startsWith(strip)) {
//                String value = props.getProperty(name);
//                String newName = name.substring(strip.length());
//                props.setProperty(newName, value);
//            }
//        }
//    }
}
