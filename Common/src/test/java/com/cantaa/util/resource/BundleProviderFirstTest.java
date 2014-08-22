package com.cantaa.util.resource;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class BundleProviderFirstTest {

    private BundleProvider bundleProvider;

    @Before
    public void setUp() throws Exception {
        bundleProvider = new BundleProvider(new MyLocaleProvider(),
                                            this.getClass().getName(),
                                            BundleProviderTest.class.getName()
        );
    }

    @Test
    public void testKeys() throws Exception {
        doTestResource("test.first.a", "A From First");
        doTestResource("test.first.b", "B From First");
        doTestResource("test.first.c", "C From Base");
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
    private static class MyLocaleProvider implements LocaleProvider {
        @Override
        public Locale getLocale() {
            return  Locale.GERMANY;
        }
    }


}
