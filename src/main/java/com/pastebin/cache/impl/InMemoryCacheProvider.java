package com.pastebin.cache.impl;

import com.pastebin.cache.ICache;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Component
public class InMemoryCacheProvider implements ICache{

    private final Map<Object, Object> map = new HashMap<>();

    @Override
    public Object getCache() {
        return map;
    }
}
