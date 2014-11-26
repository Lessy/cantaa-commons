package com.cantaa.util.cache;

/**
 * Classes who's instances can be cached, may implement this interface
 *
 * @author Hans Lesmeister
 */
public interface Cacheable {

    /**
     * @return Array with keys. Each of them uniquely identifies the cached object (inside the Class-Namespace). In most
     *         cases you will probably return one value, i.e. the primary key. In addition you can add more keys like an article-
     *         number
     */
    Object[] getCacheIdentifiers();
}
