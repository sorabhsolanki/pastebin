package com.pastebin.service.provider;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 */
public interface IStorage {

    String storeFile(MultipartFile file, String docID);

    Resource loadFileAsResource(String fileName);
}
