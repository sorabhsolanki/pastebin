package com.pastebin.cache;


import com.pastebin.annotation.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class CacheManager {

    private static CacheManager cacheManager;

    private Map<String, Object> map = new HashMap<>();

    private CacheManager(){}

    public static CacheManager getInstance(){
        if(cacheManager == null){
            synchronized (CacheManager.class){
                if(cacheManager == null){
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    public void set(Object obj){
        if(!obj.getClass().isAnnotationPresent(Cache.class)){
            throw new RuntimeException("Cache annotation is not present.");
        }
        map.put(obj.getClass().getAnnotation(Cache.class).name(), obj);
    }

    public <T> T getCache(Class<T> cacheClass ){
        if(! cacheClass.isAnnotationPresent(Cache.class)){
            throw new RuntimeException("Cache annotation is not present.");
        }
        return (T) map.get(cacheClass.getAnnotation(Cache.class).name());
    }

}
