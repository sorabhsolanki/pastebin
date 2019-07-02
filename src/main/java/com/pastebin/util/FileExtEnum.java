package com.pastebin.util;

/**
 */
public enum FileExtEnum {

    FILE("file"), IMAGE("image");

    private final String fileType;

    FileExtEnum(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
