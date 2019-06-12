package com.pastebin.handler;

import com.pastebin.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 */
@Component
@DependsOn("loadOnStartup")
public class FileStorageHandler {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileStorageHandler(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public String upload(final MultipartFile file){
        return fileStorageService.storeFileOnDirectory(file);
    }


}
