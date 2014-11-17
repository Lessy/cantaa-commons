package com.cantaa.util.cache;

/**
 * Classes who's instances can be cached, may implement this interface
 * @author Hans Lesmeister
 */
public interface Cacheable {

    /**
     * @return uniquely identifies the cached object (inside the Class-Namespace)
     */
    Object getCacheIdentifier();
}
