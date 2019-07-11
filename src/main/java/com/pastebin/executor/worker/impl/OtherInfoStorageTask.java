package com.pastebin.executor.worker.impl;

import com.pastebin.dto.OtherInfoDto;
import com.pastebin.entity.DocumentEntity;
import com.pastebin.executor.ExecutorTaskResult;
import com.pastebin.executor.worker.ITask;
import com.pastebin.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 */
public class OtherInfoStorageTask extends ITask {

    private static final Logger LOG = LoggerFactory.getLogger(OtherInfoStorageTask.class);

    private final ApplicationContext applicationContext;
    private final OtherInfoDto otherInfoDto;


    public OtherInfoStorageTask(ApplicationContext applicationContext, OtherInfoDto otherInfoDto) {
        this.applicationContext = applicationContext;
        this.otherInfoDto = otherInfoDto;
    }


    /*
       method will do the following operations:
       1. insert the text, url and title of the page into DB.
       2. check whether the provided docID is present inside the DB.
       3. update information like text, url and title if present else insert.
     */
    @Override
    protected void process() {
        ExecutorTaskResult executorTaskResult = applicationContext.getBean(ExecutorTaskResult.class);
        DocumentRepository documentRepository = applicationContext.getBean(DocumentRepository.class);

        try {
            Optional<List<DocumentEntity>> optionalDocumentEntityList = documentRepository.getDocument(getDocID());
            DocumentEntity documentEntity;
            if(optionalDocumentEntityList.isPresent()){
                documentEntity = optionalDocumentEntityList.get().get(0);
                documentEntity.setDocumentText(otherInfoDto.getText());
                documentEntity.setBookmarkUrl(otherInfoDto.getUrl());
                documentEntity.setTitle(otherInfoDto.getTitle());
                documentRepository.update(documentEntity);
            }else {
                documentEntity = new DocumentEntity(getDocID(), otherInfoDto.getText(), otherInfoDto.getTitle(),
                        otherInfoDto.getUrl());
                documentRepository.insert(documentEntity);
            }
            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.OK,
                    "Successfully uploaded.", documentEntity.getDocumentId()));

        } catch (Exception e) {
            executorTaskResult.insert(getReferenceId(), new ExecutorTaskResult.ResultDto(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not upload the info : " + e.getMessage()));
        }
    }
}
