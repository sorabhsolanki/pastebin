package com.pastebin.service;

import com.pastebin.service.provider.IStorage;
import com.pastebin.util.CacheConstants;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 */
public abstract class AbstractFileStorage {

    private final Map<String, IStorage> storageMap;


    protected AbstractFileStorage(Map<String, IStorage> storageMap) {
        this.storageMap = storageMap;
    }

    public IStorage getFileStorageProvider(){
        return storageMap.get(CacheConstants.FILE_STORAGE);
    }

    public abstract String storeFileOnDirectory(MultipartFile file, String docID);

    public abstract Resource loadFileAsResource(String fileName);
}
