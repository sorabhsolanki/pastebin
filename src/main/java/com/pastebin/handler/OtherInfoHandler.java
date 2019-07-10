package com.pastebin.handler;

import com.pastebin.executor.ExecutorTaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class OtherInfoHandler {

    private final ExecutorTaskResult executorTaskResult;

    @Autowired
    public OtherInfoHandler(ExecutorTaskResult executorTaskResult) {
        this.executorTaskResult = executorTaskResult;
    }


}
