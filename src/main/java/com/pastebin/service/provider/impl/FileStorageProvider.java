package com.pastebin.service.provider.impl;

import com.pastebin.config.FileStorageProperties;
import com.pastebin.executor.IExecutor;
import com.pastebin.executor.worker.ITask;
import com.pastebin.executor.worker.impl.SaveOnDirectory;
import com.pastebin.service.FileStorageService;
import com.pastebin.service.provider.IStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 */
@Component
public class FileStorageProvider implements IStorage{

    private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);

    private final FileStorageProperties fileStorageProperties;
    private final ApplicationContext applicationContext;
    private final IExecutor executor;

    @Autowired
    public FileStorageProvider(FileStorageProperties fileStorageProperties, ApplicationContext applicationContext,
                               @Qualifier("executorImpl") IExecutor executor) {
        this.fileStorageProperties = fileStorageProperties;
        this.applicationContext = applicationContext;
        this.executor = executor;
    }

    @Override
    public String storeFile(MultipartFile file) {
        Path fileStorageLocation = Paths.get(fileStorageProperties.getStorageLocation())
                .toAbsolutePath().normalize();
        createDirectoryIfNotExist(fileStorageLocation);
        LOG.info("File will be saved on : " + fileStorageLocation);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        ITask task = new SaveOnDirectory(applicationContext, file, fileStorageLocation, fileName);
        return executor.executeTask(task);
    }


    //TODO : here fileStorageLocation will be fetched from the DB. rewrite the logic
    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            /*Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }*/

            return new UrlResource("remove this line after writing the logic.");
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }


    }

    private void createDirectoryIfNotExist(final Path fileStorageLocation) {
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
}
