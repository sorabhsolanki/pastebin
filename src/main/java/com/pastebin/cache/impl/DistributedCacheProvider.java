package com.pastebin.cache.impl;

import com.pastebin.cache.ICache;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class DistributedCacheProvider implements ICache{

    @Override
    public Object getCache() {
        return null;
    }
}
