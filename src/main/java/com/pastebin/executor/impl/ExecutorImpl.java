package com.pastebin.executor.impl;

import com.pastebin.executor.IExecutor;
import com.pastebin.executor.worker.ITask;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 */
@Service
@DependsOn("propertyReader")
public class ExecutorImpl extends IExecutor {

    @Override
    public String executeTask(ITask worker) {
        getExecutorService().submit(worker);
        return worker.getReferenceId();
    }
}
