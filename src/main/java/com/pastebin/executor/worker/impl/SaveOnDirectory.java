package com.pastebin.executor.worker.impl;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.objects.impl.FileExtensionCache;
import com.pastebin.entity.DocumentEntity;
import com.pastebin.executor.ExecutorTaskResult;
import com.pastebin.executor.worker.ITask;
import com.pastebin.repository.DocumentRepository;
import com.pastebin.util.FileExtEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

/**
 */
public class SaveOnDirectory extends ITask {

    private static final Logger LOG = LoggerFactory.getLogger(SaveOnDirectory.class);

    private final CacheManager cacheManager = CacheManager.getInstance();
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

    /*
       TODO:
       method will do the following operations:
       1. copy the content of multipart file inside the directory.
       2. check whether the provided docID is present inside the DB.
       3. update information like is_file, is_image, file_size, file_extension, directory_location
     */
    @Override
    protected void process() {
        ExecutorTaskResult executorTaskResult = applicationContext.getBean(ExecutorTaskResult.class);
        DocumentRepository documentRepository = applicationContext.getBean(DocumentRepository.class);

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("File uploaded with file name : " + fileName);

            FileExtensionCache fileExtensionCache = cacheManager.getCache(FileExtensionCache.class);
            String extension = file.getOriginalFilename().split("\\.")[1];
            FileExtEnum fileExtEnum = fileExtensionCache.checkForFileOrImage(extension);
            Optional<List<DocumentEntity>> optionalDocumentEntityList = documentRepository.getDocument(getDocID());
            if(optionalDocumentEntityList.isPresent()){
                DocumentEntity documentEntity = optionalDocumentEntityList.get().get(0);
                documentEntity.setFileType(fileExtEnum.getFileType());
                documentEntity.setFileSize(file.getSize());
                documentEntity.setFileExtension(extension);
                documentEntity.setDirectoryPath(fileStorageLocation.toString());
                documentRepository.update(documentEntity);
            }


            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.OK,
                    "Successfully uploaded.", fileName, file.getSize(), ExecutorTaskResult.Storage.FILE));


        } catch (IOException e) {
            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not store file due to : " + e.getMessage()));
        }
    }
}
