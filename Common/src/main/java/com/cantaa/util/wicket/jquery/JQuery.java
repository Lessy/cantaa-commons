package com.cantaa.util.wicket.jquery;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.util.string.Strings;

import com.cantaa.util.Reject;

/**
 * API for rendering several JQuery-function calls. Every function call is represented by a
 * JQSnippet. Calls can be chained by just adding snippets to the JQuery-Instance
 * <p/>
 * Usage:
 * <pre>
 *    Component anim = new WebMarkupContainer("somediv");
 *    anim.setOutputMarkupId(true);
 *    JQuery jq = JQuery.$(clickArea).slideUp(300);
 *    String s = jq.render(); // => "$('#div12').slideUp(300);"
 * </pre>
 *
 * @see JQSnippet
 * @see JQueryBehavior
 * @author Hans Lesmeister
 */
public class JQuery implements JQRenderable {

    private final Component component;
    private final String tag;

    List<JQSnippet> snippets = new ArrayList<JQSnippet>();

    /**
     * Use JQuery.$(...) to instantiate JQuery
     * @param component The component on which the Instance will work, eg. its Markup-Id
     */
    private JQuery(Component component) {
        super();
        Reject.ifNull(component, "component is null");
        this.component = component;
        tag = null;
    }

    /**
     * Use JQuery.$(...) to instantiate JQuery
     * @param tag The string on which the Instance will work, like 'div' or '.someClass'
     */
    private JQuery(String tag) {
        super();
        Reject.ifTrue(Strings.isEmpty(tag), "tag is null or empty");
        this.tag = tag;
        component = null;
    }

    /**
     * Factory-Method to create a JQuery-Instance
     * @param component
     * @return Instance
     */
    public static JQuery $(Component component) {
        return new JQuery(component);
    }

    /**
     * Factory-Method to create a JQuery-Instance
     * @param tag
     * @return Instance
     */
    public static JQuery $(String tag) {
        return new JQuery(tag);
    }

    public JQuery addSnippet(JQSnippet snippet) {
        snippets.add(snippet);
        return this;
    }

    /**
     * Render the JQuery-Call to a string. Internall this calls <code>render(true)</code>
     */
    public String render() {
        return render(true);
    }

    /**
     * Render the JQuery-Call to a string.
     * @param withClosingSemicolon if true (def.): add a semicolon (;) to the end of the call
     */
    public String render(boolean withClosingSemicolon) {
        StringBuilder sb = new StringBuilder();

        sb.append("$('");

        if (component != null) {
            sb.append("#").append(component.getMarkupId());
        } else if (tag != null) {
            sb.append(tag);
        }

        sb.append("')");

        for (JQSnippet snippet : snippets) {
            sb.append(".");
            sb.append(snippet.render());
        }

        if (withClosingSemicolon) {
            sb.append(";");
        }

        return sb.toString();
    }

    public JQuery click(JQFunction function) {
        addSnippet(new JQSnippet("click", function));
        return this;
    }

    public JQuery slideUp(int milliseconds) {
        addSnippet(new JQSnippet("slideUp", milliseconds));
        return this;
    }

    public JQuery slideDown(int milliseconds) {
        addSnippet(new JQSnippet("slideDown", milliseconds));
        return this;
    }

    /**
     * Convenient to slideUp or slideDown dependant on the passed boolean
     * @param doSlideUp true: slideUp is called, false: slideDown is called
     * @param milliseconds Passed on to one of the called functions
     * @return this for chaining
     */
    public JQuery slide(boolean doSlideUp, int milliseconds) {
        if (doSlideUp) {
            return slideUp(milliseconds);
        } else {
            return slideDown(milliseconds);
        }
    }

    /**
     * Covenient do show or hide with the passed boolean
     * @param doShow true: show, false: hide
     * @return this for chaining
     */
    public JQuery showHide(boolean doShow) {
        if (doShow) {
            return show();
        } else {
            return hide();
        }
    }

    public JQuery hide() {
        addSnippet(new JQSnippet("hide"));
        return this;
    }

    public JQuery show() {
        addSnippet(new JQSnippet("show"));
        return this;
    }

    public JQuery attr(String attributeName) {
        addSnippet(new JQSnippet("attr", attributeName));
        return this;
    }

    public JQuery attr(String attributeName, String value) {
        addSnippet(new JQSnippet("attr", attributeName, value));
        return this;
    }

    public JQuery trigger(String trigger) {
        addSnippet(new JQSnippet("trigger", trigger));
        return this;
    }

    public JQuery enable() {
        addSnippet(new JQSnippet("prop", "disabled", false));
        return this;
    }

    public JQuery disable() {
        addSnippet(new JQSnippet("prop", "disabled", true));
        return this;
    }

    public JQuery focus() {
        addSnippet(new JQSnippet("focus"));
        return this;
    }

    public JQuery select() {
        addSnippet(new JQSnippet("select"));
        return this;
    }

    public JQuery scrollTop(int position) {
        addSnippet(new JQSnippet("scrollTop", position));
        return this;
    }


}
