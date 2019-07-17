package com.pastebin.handler;

import com.pastebin.exception.GeneralException;
import com.pastebin.service.provider.IStorage;
import com.pastebin.service.provider.impl.FileStorageProvider;
import com.pastebin.service.provider.impl.MongoDBStorageProvider;
import com.pastebin.util.CacheConstants;
import com.pastebin.util.ErrorCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class StorageFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public StorageFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, IStorage> initializeStorageProviders(String[] caches) throws GeneralException {
        Map<String, IStorage> storageMap = new HashMap<>();

        for (String name : caches) {
            switch (name) {
                case CacheConstants.FILE_STORAGE:
                    storageMap.put(CacheConstants.FILE_STORAGE, applicationContext.getBean(FileStorageProvider.class));
                    break;
                case CacheConstants.CACHE_DISTRIBUTED_REDIS:
                    storageMap.put(CacheConstants.CACHE_DISTRIBUTED_REDIS, applicationContext.getBean(MongoDBStorageProvider.class));
                    break;
                case CacheConstants.CACHE_DISTRIBUTED_AEROSPIKE:
                    throw new GeneralException(ErrorCodeConstants.UNSUPPORTED_BACKEND_OPERATION, "Operation not supported.");
            }
        }

        return storageMap;
    }
}
