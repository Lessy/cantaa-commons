package com.cantaa.util.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

/**
 * Sets the visibility of the attached component, controlled by the Boolean Model
 *
 * @author Hans Lesmeister
 */
public class VisibilityBehavior extends Behavior {

    private IModel<Boolean> visibilityModel;
    private final boolean negate;

    public VisibilityBehavior(IModel<Boolean> visibilityModel) {
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
    public VisibilityBehavior(IModel<Boolean> visibilityModel, boolean negate) {
        super();
        this.visibilityModel = visibilityModel;
        this.negate = negate;
    }

    @Override
    public void onConfigure(Component component) {
        if (visibilityModel != null) {
            if (negate) {
                component.setVisible(!visibilityModel.getObject());
            } else {
                component.setVisible(visibilityModel.getObject());
            }
        }
    }
}
