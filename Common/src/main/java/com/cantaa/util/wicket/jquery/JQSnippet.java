package com.cantaa.util.wicket.jquery;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.cantaa.util.Reject;

/**
 * Encapsulation of one JQuery function call
 * @author Hans Lesmeister
 */
public class JQSnippet implements JQRenderable {

    private final Object[] parameters;
    private IModel<String> snippetModel;

    public JQSnippet(String call, Object... parameters) {
        Reject.ifNull(call, "call is null");
        snippetModel = Model.of(call);
        this.parameters = parameters;
    }

    public JQSnippet(IModel<String> snippetModel, Object... parameters) {
        this.parameters = parameters;
        Reject.ifNull(snippetModel, "snippetModel is null");
        this.snippetModel = snippetModel;
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append(snippetModel.getObject());

        sb.append("(");

        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];

                if (i > 0) {
                    sb.append(", ");
                }

                if (parameter == null) {
                    sb.append("null");

                } else if (parameter instanceof Number) {
                    sb.append(parameter);

                } else if (parameter instanceof CharSequence) {
                    sb.append("'").append(parameter).append("'");

                } else if (parameter instanceof JQRenderable) {
                    sb.append(((JQRenderable) parameter).render());

                }
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
