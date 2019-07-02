package com.pastebin.service.provider.impl;

import com.pastebin.service.provider.IStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 */
@Component
public class MongoDBStorageProvider implements IStorage{

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBStorageProvider.class);

    @Override
    public String storeFile(MultipartFile file, String docID) {
        LOG.warn("No definition provided for this method.");
        return null;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        LOG.warn("No definition provided for this method.");
        return null;
    }
}
