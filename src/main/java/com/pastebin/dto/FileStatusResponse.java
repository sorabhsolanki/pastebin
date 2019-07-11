package com.pastebin.dto;

/**
 */
public class FileStatusResponse extends PasteItStatusResponse{

    private String fileStatusUri;
    private String fileType;

    public FileStatusResponse(String fileReferenceID, String fileStatusUri, String fileType, String message) {
        super(fileReferenceID, message);
        this.fileStatusUri = fileStatusUri;
        this.fileType = fileType;
    }

    public String getFileStatusUri() {
        return fileStatusUri;
    }

    public String getFileType() {
        return fileType;
    }

}
