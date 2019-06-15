package com.pastebin.cache.objects.impl;


import com.pastebin.annotation.Cache;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.AbstractInMemoryCache;

/**
 */
@Cache(name = "directoryCache")
public class DirectoryCache extends AbstractInMemoryCache {

    public DirectoryCache(ICache iCache) {
        super(iCache);
    }

    @Override
    public void insert(String key, String value){
        getCacheStorage().put(key, value);
    }

    @Override
    public String get(final String key){
        return getCacheStorage().get(key);
    }
}


