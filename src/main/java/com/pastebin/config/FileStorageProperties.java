package com.pastebin.config;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.objects.impl.DirectoryCache;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
@Service
@DependsOn("loadOnStartup")
public class FileStorageProperties {

    private final CacheManager cacheManager;
    private final Random random;
    private final int codec = 10000;

    private final DirectoryCache directoryCache;
    private List<String> locations;

    public FileStorageProperties() {
        this.cacheManager = CacheManager.getInstance();
        this.random = new Random();
        this.directoryCache = cacheManager.getCache(DirectoryCache.class);
        initializeLocations();
    }

    public String getStorageLocation(){
        return locations.get(random.nextInt(codec) % directoryCache.getSize());
    }

    private void initializeLocations() {
        locations = new ArrayList<>(directoryCache.getSize());
        directoryCache.getAllKeys().forEach(key -> locations.add(key));
    }
}
