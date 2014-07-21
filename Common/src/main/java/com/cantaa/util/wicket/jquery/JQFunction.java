package com.cantaa.util.wicket.jquery;

/**
 * Encapsulation of a java script function, mostly used to pass as callback function
 * to any JQuery-calls like "click"
 * @author Hans Lesmeister
 */
public class JQFunction implements JQRenderable {

    private String functionBody;

    public JQFunction() {
        super();
    }

    public JQFunction(String functionBody) {
        super();
        this.functionBody = functionBody;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();

        sb.append("function(event) {");

        String body = getFunctionBody();
        if (body != null) {
            sb.append(body);
        }

        sb.append("}");

        return sb.toString();
    }

    public String getFunctionBody() {
        return functionBody;
    }

    public JQFunction setFunctionBody(String functionBody) {
        this.functionBody = functionBody;
        return this;
    }
}
