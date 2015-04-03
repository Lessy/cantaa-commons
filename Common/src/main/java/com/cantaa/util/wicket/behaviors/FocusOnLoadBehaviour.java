package com.cantaa.util.wicket.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * @author Hans Lesmeister
 */
public class FocusOnLoadBehaviour extends Behavior
{
   private Component component;

   private boolean selectAfterFocus;

   /**
    * Default Constructor
    */
   public FocusOnLoadBehaviour() {
      this.selectAfterFocus = true;
   }

   /**
    * Constructor
    * @param selectAfterFocus if true (default) then the component is selected (text) after
    *   receiving the focus
    */
   public FocusOnLoadBehaviour(boolean selectAfterFocus) {
      this.selectAfterFocus = selectAfterFocus;
   }

   public void bind(Component component)
   {
      this.component = component;
      component.setOutputMarkupId(true);
   }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        response.render(new OnDomReadyHeaderItem(renderJavaScript(component.getMarkupId(), selectAfterFocus)));
    }

    //   public void renderHead(IHeaderResponse iHeaderResponse) {
//      super.renderHead(iHeaderResponse);
//      iHeaderResponse.renderOnLoadJavascript(renderJavaScript(component.getMarkupId(), selectAfterFocus));
//   }

   public static String renderJavaScript(String markupId, boolean selectAfterFocus) {
      return  "try {\n" +
              "  var el = document.getElementById('" + markupId + "');\n" +
              "  el.focus();\n" +
              (selectAfterFocus ? "  el.select();\n" : "") +
              "} catch (e) { }\n";
   }

   public boolean isTemporary() {
      // remove the behavior after component has been rendered
      return false;
   }
}
