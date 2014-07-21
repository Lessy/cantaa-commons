package com.cantaa.util.wicket.jquery;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * Behavior that add a OnDomReady-item for the passed JQuery-Item
 * @author Hans Lesmeister
 */
public class JQueryBehavior extends Behavior {
    private JQuery jQuery;

    public JQueryBehavior() {
        super();
    }

    public JQueryBehavior(JQuery jQuery) {
        super();
        this.jQuery = jQuery;
    }

    public void setJQuery(JQuery jQuery) {
        this.jQuery = jQuery;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        if (jQuery != null) {
            response.render(new OnDomReadyHeaderItem(jQuery.render()));
        }
    }
}
