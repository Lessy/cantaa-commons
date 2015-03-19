package com.cantaa.util.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cantaa.util.Reject;

/**
 * Handles loading resource-strings from a bundle
 * <p/>
 * Usage:
 * <pre><code>
 *     provider = new BundleProvider("BundleName");
 *     String value = provider.getString("key", "parameter");
 * </code></pre>
 *
 * @author Hans Lesmeister
 */
public class BundleProvider {
    private static final Logger log = LoggerFactory.getLogger(BundleProvider.class);

    private static final ResourceBundle.Control control = new MyBundleControl();
    private static final List<String> CONTROL_FORMATS = Arrays.asList("properties.xml");

    private final Set<String> bundleNames = new HashSet<String>();
    private final Map<String, URLClassLoader> bundleLoaders = new HashMap<String, URLClassLoader>();
    private LocaleProvider localeProvider;

    /**
     * Constructor for backwards compatibility
     * @deprecated Use constructor <code>BundleProvider(LocaleProvider, String[])</code> instead
     * @param bundleName
     * @param localeProvider
     */
    public BundleProvider(String bundleName, LocaleProvider localeProvider) {
        this(localeProvider, bundleName);
    }

    public BundleProvider(String bundleName) {
        this(null, bundleName);
    }

    public BundleProvider(LocaleProvider localeProvider, String... bundleNames) {
        super();
        Reject.ifNull(bundleNames, "bundleNames is null");
        Reject.ifTrue(bundleNames.length == 0, "bundleNames is empty");

        this.localeProvider = localeProvider;
        for (String bundleName : bundleNames) {
            this.bundleNames.add(bundleName);
        }
    }

    public void addBundleLoader(String bundleName, URLClassLoader bundleLoader) {
        Reject.ifNull(bundleName, "bundleName is null");
        Reject.ifNull(bundleLoader, "bundleLoader is null");

        bundleNames.add(bundleName);
        bundleLoaders.put(bundleName, bundleLoader);
    }

    public String getString(String key, Object... objects) {
        return getString(null, key, objects);
    }

    public void setLocaleProvider(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

    /**
     * Get a localized resource string from the Bundle
     *
     * @param locale  The locale to use. If null then locale from the locale provider is used or the current default locale is used
     * @param key     Key of the string in the bundle
     * @param objects Optional array of values to feed MessageFormat for placeholders in the resource strings
     * @return The value that was retrieved from the bundle. If the key could not be found then no exception is thrown. Instead,
     *         the key itself is returned
     */
    public String getString(Locale locale, String key, Object... objects) {
        Locale usedLocale = determineLocale(locale);

        String value = key;

        for (String bundleName : bundleNames) {
            URLClassLoader bundleLoader = bundleLoaders.get(bundleName);
            ResourceBundle rb;
            if (bundleLoader == null) {
                rb = ResourceBundle.getBundle(bundleName, usedLocale, control);
            } else {
                rb = ResourceBundle.getBundle(bundleName, usedLocale, bundleLoader, control);
            }

            if (rb.containsKey(key)) {
                value = rb.getString(key);
                break;
            }
        }

        if ((value != null) && (objects != null) && (objects.length != 0)) {
            final MessageFormat format = new MessageFormat(value, usedLocale);
            value = format.format(objects);
        }

        return value;
    }

    /**
     * Determine a locale to use
     *
     * @param locale
     * @return the applied locale or, if null, the locale provided by the locale provider or, if null, the default locale
     */
    private Locale determineLocale(Locale locale) {
        if (locale != null) {
            return locale;
        }

        if (localeProvider == null) {
            return Locale.getDefault();
        }

        Locale usedLocale = localeProvider.getLocale();
        if (usedLocale == null) {
            return Locale.getDefault();
        }

        return usedLocale;
    }

    private static class MyBundleControl extends ResourceBundle.Control {

        public List<String> getFormats(String baseName) {
            if (baseName == null) {
                throw new NullPointerException();
            }
            return CONTROL_FORMATS;
        }

        public ResourceBundle newBundle(String baseName,
                                        Locale locale,
                                        String format,
                                        ClassLoader loader,
                                        boolean reload)
                throws IllegalAccessException,
                       InstantiationException,
                       IOException
        {
            if (baseName == null || locale == null
                || format == null || loader == null) {
                throw new NullPointerException();
            }
            ResourceBundle bundle = null;
            if (format.equals("properties.xml")) {
                String bundleName = toBundleName(baseName, locale);
                String resourceName = toResourceName(bundleName, format);
                log.debug("bundleName to load: {}, resourceName to load {}", bundleName, resourceName);

                InputStream stream = null;
                if (reload) {
                    log.debug("Reloading the resource");
                    URL url = loader.getResource(resourceName);
                    if (url != null) {
                        URLConnection connection = url.openConnection();
                        if (connection != null) {
                            // Disable caches to get fresh data for
                            // reloading.
                            connection.setUseCaches(false);
                            stream = connection.getInputStream();
                        }
                    }
                }
                else {
                    log.debug("About to load the resource");
                    stream = loader.getResourceAsStream(resourceName);
                }
                if (stream != null) {
                    log.debug("Loading the resource");
                    BufferedInputStream bis = new BufferedInputStream(stream);
                    try {
                        bundle = new XMLResourceBundle(bis);
                        log.debug("Resource is loaded successfully");
                    }
                    finally {
                        bis.close();
                    }
                }
            }
            return bundle;
        }

    }

    private static class XMLResourceBundle extends ResourceBundle {

        private Properties props;

        XMLResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        protected Object handleGetObject(String key) {
            return props.getProperty(key);
        }

        @SuppressWarnings("unchecked")
        public Enumeration<String> getKeys() {
            return (Enumeration<String>) props.propertyNames();
        }
    }
}

