package com.pastebin.exception;


import com.pastebin.util.ErrorCodeConstants;

public class GeneralException extends Exception{

    private ErrorCodeConstants errorCodeConstants;

    public GeneralException(String message){
        super(message);
    }

    public GeneralException(ErrorCodeConstants errorCodeConstants, String message){
        super(message);
        this.errorCodeConstants = errorCodeConstants;
    }

    public GeneralException(String message, Throwable cause){
        super(message, cause);
    }

    public ErrorCodeConstants getErrorCodeConstants() {
        return errorCodeConstants;
    }
}
