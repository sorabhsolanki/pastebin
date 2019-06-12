package com.pastebin.executor.worker.impl;

import com.pastebin.executor.ExecutorTaskResult;
import com.pastebin.executor.worker.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 */
public class SaveOnDirectory extends ITask {

    private static final Logger LOG = LoggerFactory.getLogger(SaveOnDirectory.class);

    private final ApplicationContext applicationContext;
    private final MultipartFile file;
    private final Path fileStorageLocation;
    private final String fileName;


    public SaveOnDirectory(ApplicationContext applicationContext, MultipartFile file, Path fileStorageLocation, String fileName) {
        this.applicationContext = applicationContext;
        this.file = file;
        this.fileStorageLocation = fileStorageLocation;
        this.fileName = fileName;
    }

    @Override
    protected void process() {
        ExecutorTaskResult executorTaskResult = applicationContext.getBean(ExecutorTaskResult.class);
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("File uploaded with file name : " + fileName);
            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.OK,
                    "Successfully uploaded.", fileName, file.getSize(), ExecutorTaskResult.Storage.FILE));
        } catch (IOException e) {
            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not store file due to : " + e.getMessage()));
        }
    }
}