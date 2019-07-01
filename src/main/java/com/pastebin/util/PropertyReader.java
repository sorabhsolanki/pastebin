package com.pastebin.util;

import com.pastebin.cache.CacheManager;
import com.video.poc.play.cache.CacheManager;
import com.video.poc.play.cache.PropertyCache;
import com.video.poc.play.exception.GeneralException;
import com.video.poc.play.model.Property;
import com.video.poc.play.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

/**
 * class for loading extension property from file_extension.properties file
 */
@Component
public class PropertyReader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyReader.class);
    private final Properties prop;
    private static final CacheManager cacheManager = CacheManager.getInstance();

    public PropertyReader() {
        this.prop = new Properties();
    }

    public void readPropertyFileAndLoadItInsideCache() {
        if (prop.isEmpty()) {
            InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("file_extension.properties");
            try {
                prop.load(input);
            } catch (IOException ex) {
                LOG.error(ex.getMessage());
                throw new RuntimeException(ex);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        LOG.error(ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        loadPropertiesInCache();
    }


    private void loadPropertiesInCache() throws GeneralException {
        PropertyCache propertyCache = new PropertyCache();
        Enumeration stringEnumeration = prop.propertyNames();
        while (stringEnumeration.hasMoreElements()) {
            String key = stringEnumeration.nextElement().toString();
            propertyCache.insert(key, prop.getProperty(key));
        }
        CacheManager.getInstance().set(propertyCache);
    }
}
