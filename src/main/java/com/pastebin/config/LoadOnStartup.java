package com.pastebin.config;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.objects.ProjectPropertyCache;
import com.pastebin.entity.ProjectPropertyEntity;
import com.pastebin.service.ProjectPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 */
@Component
public class LoadOnStartup {

    private static final Logger log = LoggerFactory.getLogger(LoadOnStartup.class);
    private static final CacheManager cacheManager = CacheManager.getInstance();

    private final ProjectPropertyService propertyService;

    @Autowired
    public LoadOnStartup(ProjectPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostConstruct
    public void init() {
        loadPropertyValue();
    }

    private void loadPropertyValue() {
        log.info(":: Initializing in-memory cache for project properties ::");
        List<ProjectPropertyEntity> projectProperties = propertyService.getAllActiveProjectProperties();
        ProjectPropertyCache propertyCache = new ProjectPropertyCache();
        for (ProjectPropertyEntity propertyEntity : projectProperties) {
            propertyCache.insert(propertyEntity.getName(), propertyEntity.getValue());
        }
        cacheManager.set(propertyCache);
    }
}
