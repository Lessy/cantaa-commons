package com.cantaa.util.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Global Cache for entities that are seldom changed
 * @author Hans Lesmeister
 */
public class Cache {

    private final ConcurrentHashMap<Class<? extends Cacheable>, Map<Object,  ? extends Cacheable>> cache = new ConcurrentHashMap<Class<? extends Cacheable>, Map<Object, ? extends Cacheable>>();

    /**
     * Read an entity from the cache
     * @param clazz Type of the entity
     * @param id id of the entity
     * @return The entity instance if found, else null
     */
    public <C extends Cacheable> C read(Class<? extends Cacheable> clazz, Object id) {
        if ((clazz == null) || (id == null)) {
            return null;
        }

        Map<Object, ? extends Cacheable> map = cache.get(clazz);
//        Map<Object, C> map = cache.get(clazz);
        if (map == null) {
            return null;
        }

        return (C) map.get(id);
    }

    /**
     * Read a list of entities from the cache
     * @param clazz Type of the entity
     * @return The entities
     */
    public <C extends Cacheable> List<C> list(Class<? extends Cacheable> clazz) {

        ArrayList<C> list = new ArrayList<C>();

        if (clazz == null) {
            return list;
        }

        Map<Object, ? extends Cacheable> map = cache.get(clazz);
//        Map<Object, C> map = cache.get(clazz);
        if (map == null) {
            return list;
        }

        Collection<C> values = (Collection<C>) map.values();
        list = new ArrayList<C>(values);

        return list;
    }

    /**
     * Put an entity into the cache
     * @param object The entity to cache
     */
    public void write(Cacheable object) {
        if (object == null) {
            return;
        }

        write(object.getClass(), object);
    }

//    public void write(Class<Identifiable> clazz, T object) {
//
//    }

    /**
     * Put an entity into the cache
     * @param clazz Type of the entity
     * @param object The entity to cache
     */
    public <R extends Cacheable> void write(Class<? extends Cacheable> clazz, R object) {
        if ((clazz == null) || (object == null) || (object.getCacheIdentifiers() == null)) {
            return;
        }

        for (Object id : object.getCacheIdentifiers()) {
            if (id != null) {
                write(clazz, object, id);
            }
        }

    }

    public <R extends Cacheable> void write(Class<? extends Cacheable> clazz, R object, Object identifier) {
        if ((clazz == null) || (object == null) || (identifier == null)) {
            return;
        }

        Map<Object, R> map = (Map<Object, R>) cache.get(clazz);
        if (map == null) {
            map = new ConcurrentHashMap<Object, R>(500);
            cache.put(clazz, map);
        }

        map.put(identifier, object);
    }

    /**
     * Remove an entity from the cache
     * @param clazz Type of the entity
     */
    public void remove(Class<? extends Cacheable> clazz, Object id) {
        if ((clazz == null) || (id == null)) {
            return;
        }

        Map<Object, ? extends Cacheable> map = cache.get(clazz);
        if (map == null) {
            return;
        }

        map.remove(id);
    }

    /**
     * Remove all entities of a specific type from the cache
     * @param clazz Type of entities to remove
     */
    public void clear(Class<? extends Cacheable> clazz) {
        if (clazz == null) {
            return;
        }

        cache.remove(clazz);
    }

    /**
     * Clear the cache completely
     */
    public void clear() {
        cache.clear();
    }
}
