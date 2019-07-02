package com.pastebin.cache.objects.impl;


import com.pastebin.annotation.Cache;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.AbstractInMemoryCache;

/**
 */
@Cache(name = "projectPropertyCache")
public class ProjectPropertyCache extends AbstractInMemoryCache<String, String> {

    public ProjectPropertyCache(ICache iCache) {
        super(iCache);
    }

    @Override
    public void insert(String key, String value){
        getCacheStorage().put(key, value);
    }

    @Override
    public String get(final String key){
        return (String) getCacheStorage().get(key);
    }
}


