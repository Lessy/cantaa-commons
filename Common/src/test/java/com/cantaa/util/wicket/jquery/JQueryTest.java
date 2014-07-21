package com.cantaa.util.wicket.jquery;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author Hans Lesmeister
 */
public class JQueryTest {
    @Test
    public void testRenderSimple() throws Exception {
        JQuery jq = JQuery.$(":test");
        assertEquals("$(':test');", jq.render());
    }

    @Test
    public void testRenderAttr() throws Exception {
        JQuery jq = JQuery.$(":test").attr("data-index");
        assertEquals("$(':test').attr('data-index');", jq.render());

        jq = JQuery.$(":test").attr("data-index", "myValue");
        assertEquals("$(':test').attr('data-index', 'myValue');", jq.render());
    }

    @Test
    public void testRenderShow() throws Exception {
        JQuery jq = JQuery.$(":test").show();
        assertEquals("$(':test').show();", jq.render());
    }

    @Test
    public void testRenderHide() throws Exception {
        JQuery jq = JQuery.$(":test").hide();
        assertEquals("$(':test').hide();", jq.render());
    }

    @Test
    public void testRenderSlide() throws Exception {
        JQuery jq = JQuery.$(":test").slideUp(123);
        assertEquals("$(':test').slideUp(123);", jq.render());

        jq = JQuery.$(":test").slideDown(200);
        assertEquals("$(':test').slideDown(200);", jq.render());

        jq = JQuery.$(":test").slide(true, 400);
        assertEquals("$(':test').slideUp(400);", jq.render());
        jq = JQuery.$(":test").slide(false, 300);
        assertEquals("$(':test').slideDown(300);", jq.render());
    }

    @Test
    public void testRenderClick() throws Exception {
        JQFunction jf = new JQFunction();
        jf.setFunctionBody("alert('Hi');");
        JQuery jq = JQuery.$(":test").click(jf);
        assertEquals("$(':test').click(function(event) {alert('Hi');});", jq.render());
    }

}
