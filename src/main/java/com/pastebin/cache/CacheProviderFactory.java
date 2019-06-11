package com.pastebin.cache;

import com.pastebin.cache.impl.DistributedCacheProvider;
import com.pastebin.cache.impl.InMemoryCacheProvider;
import com.pastebin.exception.GeneralException;
import com.pastebin.util.CacheConstants;
import com.pastebin.util.ErrorCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Component
@DependsOn("loadOnStartup")
public class CacheProviderFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public CacheProviderFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, ICache> initializeCacheProviders(String[] caches) throws GeneralException {
        Map<String, ICache> cacheMap = new HashMap<>();

        for(String name : caches){
            switch (name){
                case CacheConstants.CACHE_IN_MEMORY :
                    cacheMap.put(CacheConstants.CACHE_IN_MEMORY, applicationContext.getBean(InMemoryCacheProvider.class));
                    break;
                case CacheConstants.CACHE_DISTRIBUTED_REDIS :
                    cacheMap.put(CacheConstants.CACHE_DISTRIBUTED_REDIS, applicationContext.getBean(DistributedCacheProvider.class));
                    break;
                case CacheConstants.CACHE_DISTRIBUTED_AEROSPIKE :
                    throw new GeneralException(ErrorCodeConstants.UNSUPPORTED_BACKEND_OPERATION, "Operation not supported.");
            }
        }

        return cacheMap;
    }
}
