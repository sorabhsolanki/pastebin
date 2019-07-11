package com.pastebin.dto;

/**
 */
public class OtherInfoStatusResponse  extends PasteItStatusResponse{

    private String statusUri;

    public OtherInfoStatusResponse(String referenceID, String statusUri, String message) {
        super(referenceID, message);
        this.statusUri = statusUri;
    }

    public String getStatusUri() {
        return statusUri;
    }

    public void setStatusUri(String statusUri) {
        this.statusUri = statusUri;
    }
}
