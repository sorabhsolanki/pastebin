package com.pastebin.cache.objects.impl;


import com.pastebin.annotation.Cache;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.AbstractInMemoryCache;

import java.util.Set;

/**
 * Class for holding directory location names
 *
 *   key                 value
 * ------------       -------------
 *  locationName          ""         // no need to keep any value
 */
@Cache(name = "directoryCache")
public class DirectoryCache extends AbstractInMemoryCache<String, String> {

    public DirectoryCache(ICache iCache) {
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

    public int getSize(){
        return getCacheStorage().size();
    }

    public Set<String> getAllKeys(){
        return getCacheStorage().keySet();
    }
}


