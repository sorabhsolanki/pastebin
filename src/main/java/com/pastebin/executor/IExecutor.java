package com.pastebin.executor;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.objects.impl.ProjectPropertyCache;
import com.pastebin.executor.worker.ITask;
import com.pastebin.util.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 */
public abstract class IExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(IExecutor.class);

    private final RejectedTaskExecutionHandler rejectedTaskExecutionHandler;
    private final int initialPoolSize;
    private final int maximumPoolSize;
    private final int keepAliveTimeInSec;
    private final int workQueueSize;


    private ExecutorService executorService;

    public IExecutor(){
        this.rejectedTaskExecutionHandler = new RejectedTaskExecutionHandler();
        ProjectPropertyCache propertyCache = CacheManager.getInstance().getCache(ProjectPropertyCache.class);
        this.initialPoolSize = Integer.parseInt(propertyCache.get(CacheConstants.INITIAL_POOL_SIZE));
        this.maximumPoolSize = Integer.parseInt(propertyCache.get(CacheConstants.MAXIMUM_POOL_SIZE));
        this.keepAliveTimeInSec = Integer.parseInt(propertyCache.get(CacheConstants.KEEP_ALIVE_TIME_IN_SEC));
        this.workQueueSize = Integer.parseInt(propertyCache.get(CacheConstants.WORK_QUEUE_SIZE));
        init();
    }

    private void init(){
        LOG.info(String.format("Making instance of ThreadPoolExecutor with initialPoolSize %s, maximumPoolSize %s and workQueueSize %s",
                initialPoolSize, maximumPoolSize, workQueueSize));
        this.executorService = new ThreadPoolExecutor(initialPoolSize, maximumPoolSize, keepAliveTimeInSec,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(workQueueSize), rejectedTaskExecutionHandler);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public abstract String executeTask(ITask worker);
}
