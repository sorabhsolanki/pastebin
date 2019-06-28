package com.pastebin.handler;

import com.pastebin.dto.UploadFileResponse;
import com.pastebin.executor.ExecutorTaskResult;
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
    private final ExecutorTaskResult executorTaskResult;

    @Autowired
    public FileStorageHandler(FileStorageService fileStorageService, ExecutorTaskResult executorTaskResult) {
        this.fileStorageService = fileStorageService;
        this.executorTaskResult = executorTaskResult;
    }

    public String upload(final MultipartFile file, final String docID){
        return fileStorageService.storeFileOnDirectory(file, docID);
    }

    public UploadFileResponse getUploadResponse(final String referenceId){
        return executorTaskResult.getUploadFileResponse(referenceId);
    }
}
