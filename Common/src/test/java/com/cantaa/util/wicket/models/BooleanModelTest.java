package com.cantaa.util.wicket.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class BooleanModelTest {

    BooleanModel booleanModel;

    @Before
    public void setUp() throws Exception {
        booleanModel = new BooleanModel();
        assertEquals(Boolean.FALSE, booleanModel.getObject());
    }

    @Test
    public void testSetTrue() throws Exception {
        booleanModel.setTrue();
        assertTrue(booleanModel.isTrue());
        assertFalse(booleanModel.isFalse());
        assertEquals(Boolean.TRUE, booleanModel.getObject());
    }

    @Test
    public void testSetFalse() throws Exception {
        booleanModel.setFalse();
        assertTrue(booleanModel.isFalse());
        assertFalse(booleanModel.isTrue());
        assertEquals(Boolean.FALSE, booleanModel.getObject());
    }

    @Test
    public void testToggle() throws Exception {
        booleanModel.setFalse();
        assertTrue(booleanModel.isFalse());
        booleanModel.toggle();
        assertFalse(booleanModel.isFalse());
        booleanModel.toggle();
        assertTrue(booleanModel.isFalse());
    }
}
