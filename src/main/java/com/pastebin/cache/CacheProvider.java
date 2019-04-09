package com.pastebin.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.cache.objects.ICache;
import com.pastebin.cache.objects.ProjectPropertyCache;
import com.pastebin.util.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 */
@Component
@DependsOn("loadOnStartup")
public class CacheProvider {

    private final ApplicationContext applicationContext;
    private final Map<Integer, String> availableImplementation;

    @Autowired
    public CacheProvider(ApplicationContext applicationContext) throws IOException {
        this.applicationContext = applicationContext;
        availableImplementation = loadCacheProviderJson();
    }

    public ICache getDistributedCache(){
        CacheManager cacheManager = CacheManager.getInstance();
        ProjectPropertyCache propertyCache = cacheManager.getCache(ProjectPropertyCache.class);
        final int cacheId = Integer.parseInt(propertyCache.get(CacheConstants.ACTIVE_CACHE_PROVIDER));
        ICache cacheImplementation = (ICache) applicationContext.getBean(availableImplementation.get(cacheId));
        return cacheImplementation;
    }

    private Map<Integer, String> loadCacheProviderJson() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = CacheProvider.class.getClassLoader().getResourceAsStream("cacheProvider.json");
        CacheProviderJson[] cacheProviderJson = objectMapper.readValue(inputStream, CacheProviderJson[].class);
        final Map<Integer, String> map = new HashMap<>();
        for(CacheProviderJson json : cacheProviderJson){
            map.put(json.id, json.name);
        }
        return map;
    }

    public static class CacheProviderJson{
        int id;
        String name;
    }
}
