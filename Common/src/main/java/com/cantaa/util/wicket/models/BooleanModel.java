package com.cantaa.util.wicket.models;

import org.apache.wicket.model.IModel;

import com.cantaa.util.Reject;

/**
 * Convenient Boolean-Model
 * @author Hans Lesmeister
 */
public class BooleanModel implements IModel<Boolean> {

   private boolean value;

   public BooleanModel() {
      value = false;
   }

   public BooleanModel(boolean value) {
      this.value = value;
   }

   @Override
   public void detach() {
      // NON
   }

   @Override
   public Boolean getObject() {
      return value;
   }

   @Override
   public void setObject(Boolean value) {
      Reject.ifNull(value, "value is null");
      this.value = value;
   }

   public void setTrue() {
      value = true;
   }

   public void setFalse() {
      value = false;
   }

   public void toggle() {
      value = !value;
   }

   public boolean isTrue() {
      return value;
   }

   public boolean isFalse() {
      return !value;
   }
}
