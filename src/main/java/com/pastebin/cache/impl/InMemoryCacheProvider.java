package com.pastebin.cache.impl;

import com.pastebin.cache.ICache;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 */
@Component
public class InMemoryCacheProvider implements ICache{

    @Override
    public Object getCache() {
        return new HashMap();
    }
}
