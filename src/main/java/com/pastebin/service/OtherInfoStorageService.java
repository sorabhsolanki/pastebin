package com.pastebin.service;

import com.pastebin.dto.OtherInfoDto;
import com.pastebin.executor.ExecutorTaskResult;
import com.pastebin.executor.IExecutor;
import com.pastebin.executor.worker.ITask;
import com.pastebin.executor.worker.impl.OtherInfoStorageTask;
import com.pastebin.executor.worker.impl.SaveOnDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 */
@Service
public class OtherInfoStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(OtherInfoStorageService.class);

    private final ExecutorTaskResult executorTaskResult;
    private final ApplicationContext applicationContext;
    private final IExecutor executor;


    public OtherInfoStorageService(ExecutorTaskResult executorTaskResult, ApplicationContext applicationContext,
                                   @Qualifier("executorImpl") IExecutor executor) {
        this.executorTaskResult = executorTaskResult;
        this.applicationContext = applicationContext;
        this.executor = executor;
    }

    public String storeInfo(final OtherInfoDto infoDto, final String docID) {
        LOG.info("Saving details : {}", infoDto);
        ITask task = new OtherInfoStorageTask(applicationContext, infoDto);
        task.setDocID(docID);
        return executor.executeTask(task);
    }
}
