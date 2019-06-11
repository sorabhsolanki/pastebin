package com.pastebin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.cache.CacheManager;
import com.pastebin.cache.CacheProviderFactory;
import com.pastebin.cache.objects.ProjectPropertyCache;
import com.pastebin.entity.ProjectPropertyEntity;
import com.pastebin.service.ProjectPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 */
@Component
public class LoadOnStartup {

    private static final Logger LOG = LoggerFactory.getLogger(LoadOnStartup.class);
    private static final CacheManager cacheManager = CacheManager.getInstance();

    private final CacheProviderFactory cacheProviderFactory;
    private final ProjectPropertyService propertyService;

    @Autowired
    public LoadOnStartup(CacheProviderFactory cacheProviderFactory, ProjectPropertyService propertyService) {
        this.cacheProviderFactory = cacheProviderFactory;
        this.propertyService = propertyService;
    }

    @PostConstruct
    public void init() {
        initializeAndPopulateConfigJsonValues();
        loadPropertyValue();
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

            for(InitialConfiguration.Data data : cacheList)
                enabledCaches



        } catch (IOException e) {
            LOG.error("Error while reading InitialConfiguration json file. {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void loadPropertyValue() {
        LOG.info(":: Initializing in-memory cache for project properties ::");
        List<ProjectPropertyEntity> projectProperties = propertyService.getAllActiveProjectProperties();
        ProjectPropertyCache propertyCache = new ProjectPropertyCache();
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
