package com.cantaa.util.wicket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import static com.cantaa.util.wicket.SafeModel.from;
import static com.cantaa.util.wicket.SafeModel.path;
import static com.cantaa.util.wicket.SafeModel.model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.cantaa.util.DateUtil;

/**
 * @author Hans Lesmeister
 */
public class SafeModelTest {

    @Test
    public void testFromBareInterface() throws Exception {
        assertEquals("data", path(from(MyBareIntf.class).getData()));
        assertEquals("longValue", path(from(MyBareIntf.class).getLongValue()));
        assertEquals("intf.longValue", path(from(MyBareHolder.class).getIntf().getLongValue()));
    }

    public static class MyBareHolder {
        MyBareIntf intf;

        public MyBareIntf getIntf() {
            return intf;
        }
    }

    public static interface MyBareIntf {
        String getData();
        Long getLongValue();
    }

    @Test
    public void testFromBoolean() throws Exception {
        TestBean bean = new TestBean();
        bean.primitiveBoolean = true;
        bean.primitiveInt = 250;

        TestBean from = from(bean);
        IModel<Boolean> model = model(from.isPrimitiveBoolean());
        IModel<Integer> model2 = model(from.getPrimitiveInt());
        assertEquals(true, model.getObject());
        assertEquals(Integer.valueOf(250), model2.getObject());
    }

    @Test
    public void testFromInteger() throws Exception {
        TestBean bean = new TestBean();
        bean.normalInt = 5;
        IModel<Integer> model = model(from(bean).getNormalInt());
        assertEquals(Integer.valueOf(5), model.getObject());
    }

    @Test
    public void testFromTimestamp() throws Exception {
        TestBean bean = new TestBean();
        Timestamp timestamp = DateUtil.createTimestamp();
        bean.timestamp = timestamp;
        IModel<Timestamp> model = model(from(bean).getTimestamp());
        assertEquals(timestamp, model.getObject());
    }

    @Test
    public void testFromBigDecimal() throws Exception {
        TestBean bean = new TestBean();
        bean.bd = new BigDecimal(5);
        IModel<BigDecimal> model = model(from(bean).getBd());
        assertEquals(new BigDecimal(5), model.getObject());
    }

    @Test
    public void testModel() throws Exception {
        TestBean bean = new TestBean();
        IModel<Integer> model = model(from(bean).getNormalInt());
        bean.setNormalInt(123);
        assertEquals(Integer.valueOf(123), model.getObject());
    }

    /**
     * Test creating few proxys for later use
     */
    @Test
    public void testMultiProxy() throws Exception {
        TestBean fromTestBean = from(TestBean.class);
        OtherBean fromOtherBean = from(OtherBean.class);

        IModel<TestBean> testBeanModel = Model.of(new TestBean().setNormalInt(1967));
        IModel<Integer> testBeanPropModel = model(testBeanModel, fromTestBean.getNormalInt());

        IModel<OtherBean> otherBeanModel = Model.of(new OtherBean().setValue("1967"));
        IModel<String> otherBeanPropModel = model(otherBeanModel, fromOtherBean.getValue());

        assertTrue(testBeanPropModel instanceof PropertyModel);
        assertTrue(otherBeanPropModel instanceof PropertyModel);


        assertEquals("1967", otherBeanPropModel.getObject());
        assertEquals(Integer.valueOf(1967), testBeanPropModel.getObject());

        otherBeanModel.getObject().setValue("1968");
        assertEquals("1968", otherBeanPropModel.getObject());
        assertEquals("1968", model(otherBeanModel, fromOtherBean.getValue()).getObject());

        testBeanModel.getObject().setNormalInt(Integer.valueOf(1968));
        assertEquals(Integer.valueOf(1968), testBeanPropModel.getObject());
        assertEquals(Integer.valueOf(1968), model(testBeanModel, fromTestBean.getNormalInt()).getObject());

    }

    @Test
    public void testModelFromObjectInstance() throws Exception {
        TestBean bean = new TestBean().setNormalInt(1967);
        IModel<Object> model = model(from(bean).getNormalInt());
        assertEquals(Integer.valueOf(1967), model.getObject());
    }

    static class OtherBean implements Serializable {
        String value;

        String getValue() {
            return value;
        }

        OtherBean setValue(String value) {
            this.value = value;
            return this;
        }
    }

    static class TestBean implements Serializable {
        String s1;
        String s2;
        Integer normalInt;
        BigInteger bi;
        BigDecimal bd;
        int primitiveInt;
        boolean primitiveBoolean;
        Timestamp timestamp;

        Timestamp getTimestamp() {
            return timestamp;
        }

        BigDecimal getBd() {
            return bd;
        }

        BigInteger getBi() {
            return bi;
        }

        boolean isPrimitiveBoolean() {
            return primitiveBoolean;
        }

        void setPrimitiveBoolean(boolean primitiveBoolean) {
            this.primitiveBoolean = primitiveBoolean;
        }

        Integer getNormalInt() {
            return normalInt;
        }

        TestBean setNormalInt(Integer normalInt) {
            this.normalInt = normalInt;
            return this;
        }

        int getPrimitiveInt() {
            return primitiveInt;
        }

        void setPrimitiveInt(int primitiveInt) {
            this.primitiveInt = primitiveInt;
        }

        String getS1() {
            return s1;
        }

        void setS1(String s1) {
            this.s1 = s1;
        }

        String getS2() {
            return s2;
        }

        void setS2(String s2) {
            this.s2 = s2;
        }
    }




}
