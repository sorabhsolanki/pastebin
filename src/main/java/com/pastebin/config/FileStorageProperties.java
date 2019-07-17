package com.pastebin.config;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.objects.impl.DirectoryCache;
import com.pastebin.entity.DirectoryEntity;
import com.pastebin.service.DirectoryService;
import com.pastebin.util.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
@Service
public class FileStorageProperties {

    private final Random random;
    private final int codec = 10000;

    private DirectoryCache directoryCache;
    private List<String> locations;

    public FileStorageProperties() {
        this.random = new Random();
    }

    public String getStorageLocation(){
        return locations.get(random.nextInt(codec) % directoryCache.getSize());
    }

    public void initializeLocations() {
        this.directoryCache = CacheManager.getInstance().getCache(DirectoryCache.class);
        locations = new ArrayList<>(directoryCache.getSize());
        directoryCache.getAllKeys().forEach(key -> locations.add(key));
    }
}
