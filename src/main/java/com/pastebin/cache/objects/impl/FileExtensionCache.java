package com.pastebin.cache.objects.impl;

import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.AbstractInMemoryCache;

/**
 */
public class FileExtensionCache extends AbstractInMemoryCache {

    protected FileExtensionCache(ICache iCache) {
        super(iCache);
    }

    @Override
    public void insert(String key, String value) {
        getCacheStorage().put(key, value);
    }

    @Override
    public String get(String key) {
        return getCacheStorage().get(key);
    }
}
