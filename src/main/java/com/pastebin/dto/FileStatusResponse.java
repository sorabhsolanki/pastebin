package com.pastebin.dto;

/**
 */
public class FileStatusResponse {

    private String fileReferenceID;
    private String fileStatusUri;
    private String fileType;
    private String message;

    public FileStatusResponse(String fileReferenceID, String fileStatusUri, String fileType, String message) {
        this.fileReferenceID = fileReferenceID;
        this.fileStatusUri = fileStatusUri;
        this.fileType = fileType;
        this.message = message;
    }

    public String getFileReferenceID() {
        return fileReferenceID;
    }

    public String getFileStatusUri() {
        return fileStatusUri;
    }

    public String getFileType() {
        return fileType;
    }

    public String getMessage() {
        return message;
    }
}
