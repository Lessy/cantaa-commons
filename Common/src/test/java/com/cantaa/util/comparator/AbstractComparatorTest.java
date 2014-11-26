package com.cantaa.util.comparator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class AbstractComparatorTest {
    @Test
    public void testCompare() throws Exception {
        Bean a = new Bean("a");
        Bean b = new Bean("b");
        assertEquals(-1, new BeanComparator().compare(a, b));
        assertEquals(1, new BeanComparator().compare(b, a));
        assertEquals(0, new BeanComparator().compare(a, a));
        assertEquals(0, new BeanComparator().compare(b, b));

        // with null
        assertEquals(0, new BeanComparator().compare(null, null));
        assertEquals(1, new BeanComparator().compare(a, null));
        assertEquals(-1, new BeanComparator().compare(null, a));

        // With null contents
        assertEquals(0, new BeanComparator().compare(new Bean(null), new Bean(null)));
        assertEquals(1, new BeanComparator().compare(new Bean("a"), new Bean(null)));
        assertEquals(-1, new BeanComparator().compare(new Bean(null), new Bean("b")));
    }

    @Test
    public void testCompareDescend() throws Exception {
        Bean a = new Bean("a");
        Bean b = new Bean("b");
        assertEquals(1, new BeanComparator(true).compare(a, b));
        assertEquals(-1, new BeanComparator(true).compare(b, a));
        assertEquals(0, new BeanComparator(true).compare(a, a));
        assertEquals(0, new BeanComparator(true).compare(b, b));

        // with null
        assertEquals(0, new BeanComparator(true).compare(null, null));
        assertEquals(-1, new BeanComparator(true).compare(a, null));
        assertEquals(1, new BeanComparator(true).compare(null, a));

        // With null contents
        assertEquals(0, new BeanComparator(true).compare(new Bean(null), new Bean(null)));
        assertEquals(-1, new BeanComparator(true).compare(new Bean("a"), new Bean(null)));
        assertEquals(1, new BeanComparator(true).compare(new Bean(null), new Bean("b")));
    }

    static class BeanComparator extends AbstractComparator<Bean> {
        BeanComparator() {
            super();
        }

        public BeanComparator(boolean descend) {
            super(descend);
        }

        @Override
        public Comparable getComparableValue(Bean bean) {
            return bean.s;
        }
    }

    static class Bean {
        Bean(String s) {
            this.s = s;
        }

        private String s;
    }
}
