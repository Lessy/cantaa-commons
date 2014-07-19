package com.cantaa.util.resource;

import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.cantaa.util.DateUtil;

/**
 * @author Hans Lesmeister
 */
public class BundleProviderTest {

    private Locale locale;
    private BundleProvider bundleProvider;

    @Before
    public void setUp() throws Exception {
        locale = Locale.GERMANY;
        bundleProvider = new BundleProvider(this.getClass().getName(), new MyLocaleProvider());
    }

    @Test
    public void testNonExistingKey() throws Exception {
        String key = "THIS KEY DOES NOT EXIST";
        doTestResource(key, key);
    }

    @Test
    public void testNullKey() throws Exception {
        try {
            doTestResource(null, null);
            fail("NPE expected");
        } catch (NullPointerException e) {
            // Okay
        }
    }

    @Test
    public void testLoadResource() throws Exception {

        doTestResource("test.headline", "Probefahrt");
        doTestResource("test.headline2", "Probefahrt 2");

        doTestResource("test.welcome", "Guten Tag {0},");
        doTestResource("test.welcome", "Guten Tag Hans,", "Hans");

        Date date = DateUtil.resetTimePart(DateUtil.createDate(2013, 11, 2));
        doTestResource("test.welcome", "Guten Tag 02.11.13 00:00,", date);
        locale = Locale.US; // With another locale
        doTestResource("test.welcome", "Guten Tag 11/2/13 12:00 AM,", date);

    }

    /**
     * Helper that performs getString(...) and does the equals-check
     */
    private void doTestResource(String key, String expected, Object... objects) {
        String s = bundleProvider.getString(key, objects);
        assertEquals(expected, s);
    }

    /**
     * Helper to dynamically provide a locale
     */
    private class MyLocaleProvider implements LocaleProvider {
        @Override
        public Locale getLocale() {
            return locale;
        }
    }


}
