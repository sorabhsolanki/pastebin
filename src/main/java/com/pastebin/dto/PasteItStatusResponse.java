package com.pastebin.dto;

/**
 */
public class PasteItStatusResponse {

    private String referenceID;
    private String message;

    public PasteItStatusResponse(String referenceID, String message) {
        this.referenceID = referenceID;
        this.message = message;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
