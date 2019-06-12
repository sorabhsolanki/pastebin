package com.pastebin.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 */
public class RejectedTaskExecutionHandler implements RejectedExecutionHandler{

    private static final Logger LOG = LoggerFactory.getLogger(RejectedTaskExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        LOG.info(String.format("%s Task rejected.", runnable.toString()));
    }
}
