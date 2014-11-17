package com.cantaa.util.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.cantaa.util.wicket.jquery.JQuery;
import com.cantaa.util.wicket.jquery.JQueryBehavior;

/**
 * Sets the visibility of the attached component only by CSS (i.e. display:none), controlled by the Boolean Model
 *
 * @author Hans Lesmeister
 */
public class ShowHideBehavior extends JQueryBehavior {

    private IModel<Boolean> visibilityModel;
    private final boolean negate;

    public ShowHideBehavior(IModel<Boolean> visibilityModel) {
        this(visibilityModel, false);
    }

    /**
     * Constructor
     *
     * @param visibilityModel The model that controls the visibility of the attached component
     * @param negate          false (def.): the component's visibility is set according the model, i.e. if the model
     *                        is true, then visibility is set to true as well. If negate = true then the opposite happens: if the
     *                        model is true then the visibility of the component is set to false
     */
    public ShowHideBehavior(IModel<Boolean> visibilityModel, boolean negate) {
        super();
        this.visibilityModel = visibilityModel;
        this.negate = negate;
    }

    @Override
    public boolean isEnabled(Component component) {
        return (visibilityModel != null);
    }

    @Override
    public void onConfigure(Component component) {
        super.onConfigure(component);

        if (visibilityModel != null) {
            JQuery jq;
            if (negate) {
                jq = JQuery.$(component).showHide(!visibilityModel.getObject());
            } else {
                jq = JQuery.$(component).showHide(visibilityModel.getObject());
            }

            setJQuery(jq);
        }
    }
}
