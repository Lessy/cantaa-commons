package com.cantaa.util.wicket.jquery;

import java.io.Serializable;

/**
 * Implementations can render itself to a String
 * @author Hans Lesmeister
 */
public interface JQRenderable extends Serializable {
    String render();
}
