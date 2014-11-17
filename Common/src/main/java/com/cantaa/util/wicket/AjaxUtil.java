package com.cantaa.util.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;

import com.cantaa.util.Reject;

/**
 * Helper-Methods around Ajax and the Ajax-Request-Target (ART)
 * @author Hans Lesmeister
 */
public class AjaxUtil {

    /**
     * @return The ART or null if there is none
     */
    public static AjaxRequestTarget getAjaxRequestTarget() {
        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        return target;
    }

    /**
     * Add component(s) to the ART. If the ART is null, nothing happens
     * @param components Components to be added.
     * @throws IllegalArgumentException if the array 'components' is null
     */
    public static void addToAjaxRequestTarget(Component... components) {
        Reject.ifNull(components, "Array 'components' is null");
        AjaxRequestTarget target = getAjaxRequestTarget();
        if (target != null) {
            target.add(components);
        }
    }

    /**
     * Append Java-Script to the ART. If ART is null, nothing happens
     * @param script Script to add. If null, nothing is added
     */
    public static void appendJavaScript(String script) {
        addJavaScriptToTarget(true, script);
    }

    /**
     * Prepend Java-Script to the ART. If ART is null, nothing happens
     * @param script Script to add. If null, nothing is added
     */
    public static void prependJavaScript(String script) {
        addJavaScriptToTarget(false, script);
    }

    private static void addJavaScriptToTarget(boolean append, String script) {
        if (script == null) {
            return;
        }

        AjaxRequestTarget target = getAjaxRequestTarget();
        if (target != null) {
            if (append) {
                target.appendJavaScript(script);
            }
            else {
                target.prependJavaScript(script);
            }
        }
    }

    /**
     * Append JQuery Java-Script to ART.
     * @param jquery jQuery expression to append.
     */
//    public static void appendJavaScript(JQuery jquery) {
//        appendJavaScript(jquery.render());
//    }
//
//    /**
//     * Append JQuery hide() Java-Script to ART.
//     * @param component component to hide.
//     */
//    public static void hide(Component component) {
//        appendJavaScript(JQuery.$(component).hide());
//    }
//
//    /**
//     * Append JQuery show() Java-Script to ART.
//     * @param component component to show.
//     */
//    public static void show(Component component) {
//        appendJavaScript(JQuery.$(component).show());
//    }

    /**
     * @deprecated Nur für Testzwecken
     */
    public static void appendAlert(String s) {
        appendJavaScript("alert('" + s + "');");
    }

    public static String callAPIBefore(String callback, String... parameters) {
        return callAPI(false, callback, parameters);
    }

    public static String callAPIAfter(String callback, String... parameters) {
        return callAPI(true, callback, parameters);
    }

    public static String createJSONArray(String... values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean isFirst = true;
        for (String value : values) {
            if (isFirst) {
                isFirst = false;
                sb.append("\"");
            } else {
                sb.append(",\"");
            }
            sb.append(value);
            sb.append("\"");
        }
        sb.append("]");
        return sb.toString();
    }


    public static String callAPI(boolean append, String callback, String... parameters) {
        String script = createJavaScriptCall(callback, parameters);

        if (append) {
            AjaxUtil.appendJavaScript(script);
        } else {
            AjaxUtil.prependJavaScript(script);
        }

        return script;
    }

    /**
     * Compose JavaScript function call
     * @param callback callback function, may contain special Wicket-After-Callback separated with "|", i.e.
     *                 "someAfterFunction|myFunction" => "someAfterFunction|myFunction(someAfterFunction)"
     * @param parameters Parameters to pass to the JavaScript-function call. All Parameters will be stringyfied
     * @return JavaScript
     */
    public static String createJavaScriptCall(String callback, String... parameters) {
        String afterCall;
        String call;

        // Special treatment of "magic" Wicket-Callback with pipe "|"
        int specialIndex = callback.indexOf("|");
        if (specialIndex >= 0) {
            afterCall = callback.substring(0, specialIndex);
            call = callback.substring(specialIndex + 1);
        } else {
            afterCall = null;
            call = callback;
        }

        StringBuilder script = new StringBuilder();

        if (afterCall != null) {
            script.append(afterCall).append("|");
        }

        script.append(call);
        script.append("(");

        if (afterCall != null) {
            script.append(afterCall);
        }

        if (parameters != null) {
            boolean first = true;
            for (String parameter : parameters) {
                if (first && (afterCall == null)) {
                    first = false;
                } else {
                    script.append(", ");
                }

                script.append("'").append(parameter).append("'");
            }
        }

        script.append(");");

        return script.toString();
    }
}
