package com.pastebin.executor.worker;

import java.util.UUID;

/**
 */
public abstract class ITask implements Runnable{

    private final String referenceId = UUID.randomUUID().toString();

    @Override
    public void run() {
        process();
    }

    protected abstract void process();

    public String getReferenceId() {
        return referenceId;
    }
}
