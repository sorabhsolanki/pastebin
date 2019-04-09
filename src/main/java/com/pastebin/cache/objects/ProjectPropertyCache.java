package com.pastebin.cache.objects;


import com.pastebin.annotation.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Cache(name = "projectPropertyCache")
public class ProjectPropertyCache {

    private final Map<String, String> propertyMap;

    public ProjectPropertyCache() {
        propertyMap = new HashMap<>();
    }

    public void insert(String key, String value){
        propertyMap.put(key, value);
    }

    public String get(final String key){
        return propertyMap.get(key);
    }
}


