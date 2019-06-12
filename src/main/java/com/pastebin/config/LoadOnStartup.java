package com.pastebin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.cache.CacheManager;
import com.pastebin.cache.CacheProviderFactory;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.impl.ProjectPropertyCache;
import com.pastebin.entity.ProjectPropertyEntity;
import com.pastebin.exception.GeneralException;
import com.pastebin.handler.StorageFactory;
import com.pastebin.service.AbstractFileStorage;
import com.pastebin.service.FileStorageService;
import com.pastebin.service.ProjectPropertyService;
import com.pastebin.service.provider.IStorage;
import com.pastebin.util.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
@Component
public class LoadOnStartup {

    private static final Logger LOG = LoggerFactory.getLogger(LoadOnStartup.class);
    private static final CacheManager cacheManager = CacheManager.getInstance();

    private final CacheProviderFactory cacheProviderFactory;
    private final StorageFactory storageFactory;
    private final ProjectPropertyService propertyService;
    private Map<String, ICache> cacheMap;
    private Map<String, IStorage> storageMap;

    @Autowired
    public LoadOnStartup(CacheProviderFactory cacheProviderFactory, StorageFactory storageFactory,
                         ProjectPropertyService propertyService) {
        this.cacheProviderFactory = cacheProviderFactory;
        this.storageFactory = storageFactory;
        this.propertyService = propertyService;
    }

    @PostConstruct
    public void init() {
        initializeAndPopulateConfigJsonValues();
        loadPropertyValue();
    }


    @Bean(name = "fileStorageService")
    public AbstractFileStorage getAbstractFileStorage(){
        if(storageMap == null)
            initializeAndPopulateConfigJsonValues();
        return new FileStorageService(storageMap);
    }

    private void initializeAndPopulateConfigJsonValues() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = LoadOnStartup.class.getClassLoader().getResourceAsStream("InitialConfiguration.json");
            InitialConfiguration initialConfiguration = objectMapper.readValue(inputStream, InitialConfiguration.class);
            List<InitialConfiguration.Data> cacheList = initialConfiguration.getCache().stream().filter(data -> data.isEnable()).collect(Collectors.toList());
            List<InitialConfiguration.Data> fileStorageList = initialConfiguration.getFileStorage().stream().filter(data -> data.isEnable()).collect(Collectors.toList());
            String[] enabledCaches = new String[cacheList.size()];
            String[] enabledStorageServices = new String[fileStorageList.size()];
            fillNames(cacheList, enabledCaches);
            fillNames(fileStorageList, enabledStorageServices);
            cacheMap = cacheProviderFactory.initializeCacheProviders(enabledCaches);
            storageMap = storageFactory.initializeStorageProviders(enabledStorageServices);
        } catch (IOException | GeneralException e) {
            LOG.error("Error while reading InitialConfiguration json file. {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void fillNames(List<InitialConfiguration.Data> dataList, String[] names){
        for(int i = 0 ; i < dataList.size(); i++){
            names[i] = dataList.get(i).getName();
        }
    }

    private void loadPropertyValue() {
        LOG.info(":: Initializing in-memory cache for project properties ::");
        List<ProjectPropertyEntity> projectProperties = propertyService.getAllActiveProjectProperties();
        ProjectPropertyCache propertyCache = new ProjectPropertyCache(cacheMap.get(CacheConstants.CACHE_IN_MEMORY));
        for (ProjectPropertyEntity propertyEntity : projectProperties) {
            propertyCache.insert(propertyEntity.getName(), propertyEntity.getValue());
        }
        cacheManager.set(propertyCache);
    }

    private static class InitialConfiguration{
        private List<Data> cache;
        private List<Data> fileStorage;

        public List<Data> getCache() {
            return cache;
        }

        public List<Data> getFileStorage() {
            return fileStorage;
        }

        private static class Data{
            private String name;
            private boolean enable;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isEnable() {
                return enable;
            }

            public void setEnable(boolean enable) {
                this.enable = enable;
            }
        }
    }
}
