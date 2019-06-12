package com.pastebin.cache.objects;

import com.pastebin.cache.ICache;

import java.util.HashMap;

/**
 */
public abstract class AbstractInMemoryCache {

    private final ICache iCache;

    protected AbstractInMemoryCache(ICache iCache) {
        this.iCache = iCache;
    }

    public HashMap<String, String> getCacheStorage() {
        return (HashMap<String, String>) iCache.getCache();
    }

    public abstract void insert(String key, String value);

    public abstract String get(final String key);
}
