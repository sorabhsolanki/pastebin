package com.pastebin.cache.objects;

import com.pastebin.cache.ICache;

import java.util.HashMap;

/**
 */
public abstract class AbstractInMemoryCache<K, V> {

    private final ICache iCache;

    protected AbstractInMemoryCache(ICache iCache) {
        this.iCache = iCache;
    }

    public HashMap getCacheStorage() {
        return (HashMap) iCache.getCache();
    }

    public abstract void insert(K key, V value);

    public abstract V get(final K key);
}
