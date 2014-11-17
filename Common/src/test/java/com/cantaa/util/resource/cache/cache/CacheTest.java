package com.cantaa.util.resource.cache.cache;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.cantaa.util.cache.Cache;
import com.cantaa.util.cache.Cacheable;

/**
 * @author Hans Lesmeister
 */
public class CacheTest {

    private Cache cache;
    private Type1 t11;
    private Type1 t12;
    private Type1 t13;
    private Type2 t21;
    private Type2 t22;

    @Before
    public void setUp() throws Exception {
        cache = new Cache();
    }

    @After
    public void tearDown() throws Exception {
        cache.clear();
    }

    /**
     * Tests writing/reading the cache AND builds a cache in favour of other test methods
     */
    @Test
    public void testReadWrite() throws Exception {
        t11 = new Type1(1L);
        t12 = new Type1(2L);
        t13 = new Type1(3L);
        t21 = new Type2(1L);
        t22 = new Type2(4L);

        assertEquals(null, cache.read(Type1.class, 1L));
        cache.write(Type1.class, t11);
        cache.write(Type1.class, t12);
        assertEquals(null, cache.read(Type1.class, 3L));
        cache.write(Type1.class, t13);
        assertEquals(t11, cache.read(Type1.class, 1L));

        cache.write(Type2.class, t21);
        cache.write(Type2.class, t22);
        assertEquals(t21, cache.read(Type2.class, 1L));
        assertEquals(null, cache.read(Type2.class, 2L));
        assertEquals(t22, cache.read(Type2.class, 4L));
    }

    @Test
    public void testRemove() throws Exception {
        testReadWrite();
        cache.remove(Type2.class, 1L);
        assertEquals(t11, cache.read(Type1.class, 1L));
        assertEquals(null, cache.read(Type2.class, 1L));
    }

    @Test
    public void testClearForType() throws Exception {
        testReadWrite();
        cache.clear(Type2.class);
        assertEquals(t11, cache.read(Type1.class, 1L));
        assertEquals(null, cache.read(Type2.class, 1L));
        assertEquals(null, cache.read(Type2.class, 4L));
        cache.clear(Type1.class);
        assertEquals(null, cache.read(Type1.class, 1L));
    }

    @Test
    public void testClearAll() throws Exception {
        testReadWrite();
        cache.clear();
        assertEquals(null, cache.read(Type1.class, 1L));
        assertEquals(null, cache.read(Type1.class, 2L));
        assertEquals(null, cache.read(Type1.class, 3L));
        assertEquals(null, cache.read(Type2.class, 1L));
        assertEquals(null, cache.read(Type2.class, 4L));
    }

    private abstract static class AbstractType implements Cacheable {
        private final Long id;

        private AbstractType(Long id) {
            this.id = id;
        }

        @Override
        public Object getCacheIdentifier() {
            return id;
        }
    }

    private static class Type1 extends AbstractType {
        private Type1(Long id) {
            super(id);
        }
    }

    private static class Type2 extends AbstractType {
        private Type2(Long id) {
            super(id);
        }
    }
}
