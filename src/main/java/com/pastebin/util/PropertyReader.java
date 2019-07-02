package com.pastebin.util;

import com.pastebin.cache.CacheManager;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.impl.FileExtensionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    public void readPropertyFileAndLoadItInsideCache(final ICache iCache) {
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
        loadPropertiesInsideFileExtensionCache(iCache);
    }


    private void loadPropertiesInsideFileExtensionCache(final ICache iCache) {
        FileExtensionCache fileExtensionCache = new FileExtensionCache(iCache);
        Enumeration stringEnumeration = prop.propertyNames();
        while (stringEnumeration.hasMoreElements()) {
            String key = stringEnumeration.nextElement().toString();
            List<String> fileExtensionValues = new LinkedList<>();
            StringTokenizer stringTokenizer = new StringTokenizer(prop.getProperty(key), ",");
            while (stringTokenizer.hasMoreTokens()) {
                fileExtensionValues.add(stringTokenizer.nextToken());
            }
            if (key.toLowerCase().equals(FileExtEnum.FILE.getFileType()))
                fileExtensionCache.insert(FileExtEnum.FILE, fileExtensionValues);
            else if(key.toLowerCase().equals(FileExtEnum.IMAGE.getFileType()))
                fileExtensionCache.insert(FileExtEnum.IMAGE, fileExtensionValues);
        }
        cacheManager.set(fileExtensionCache);
    }
}
