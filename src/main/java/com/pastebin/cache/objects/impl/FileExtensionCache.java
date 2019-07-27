package com.pastebin.cache.objects.impl;

import com.pastebin.annotation.Cache;
import com.pastebin.cache.ICache;
import com.pastebin.cache.objects.AbstractInMemoryCache;
import com.pastebin.util.FileExtEnum;

import java.util.Collections;
import java.util.List;

/**
 */
@Cache(name = "fileExtensionCache")
public class FileExtensionCache extends AbstractInMemoryCache<FileExtEnum, List<String>> {

    public FileExtensionCache(ICache iCache) {
        super(iCache);
    }

    @Override
    public void insert(FileExtEnum key, List<String> value) {
        Collections.sort(value); // sort the list so that binary search can be applied at the searching time.
        getCacheStorage().put(key.getFileType(), value);
    }

    @Override
    public List<String> get(FileExtEnum key) {
        return (List<String>) getCacheStorage().get(key.getFileType());
    }

    public FileExtEnum checkForFileOrImage(String extension){
        List<String> stringListForFile = (List<String>) getCacheStorage().get(FileExtEnum.FILE.getFileType());
        FileExtEnum result = checkUtil(stringListForFile, extension, FileExtEnum.FILE);
        if(result != null)
            return result;
        return checkUtil((List<String>) getCacheStorage().get(FileExtEnum.IMAGE.getFileType()), extension, FileExtEnum.IMAGE);

    }

    private FileExtEnum checkUtil(List<String> stringList, String extension, FileExtEnum key) {
        if(stringList.isEmpty())
            return null;

        int i = 0;
        int j = extension.length();

        while (i <= j){
            int mid= (i + j)/2;

            if(stringList.get(mid).equals(extension))
                return key;

            if(key.getFileType().compareTo(stringList.get(mid)) < 1){
                j = mid - 1;
            }else {
                i = mid + 1;
            }
        }

        return null;
    }
}
