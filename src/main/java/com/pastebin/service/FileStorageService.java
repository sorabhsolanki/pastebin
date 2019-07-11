package com.pastebin.service;

import com.pastebin.service.provider.IStorage;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 */
@Service
public class FileStorageService extends AbstractFileStorage{

    public FileStorageService(Map<String, IStorage> storageMap) {
        super(storageMap);
    }

    @Override
    public String storeFileOnDirectory(final MultipartFile file, final String docID) {
        return getFileStorageProvider().storeFile(file, docID);
    }

    @Override
    public Resource loadFileAsResource(final String fileName) {
        return getFileStorageProvider().loadFileAsResource(fileName);
    }
}
