package com.pastebin.executor;

import com.pastebin.dto.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * class for holding executor tasks result.
 * key                         value
 * -----                    --------------
 * task reference Id           Object
 */
@Component
public class ExecutorTaskResult {

    private final ConcurrentMap<String, ResultDto> CONCURRENT_MAP = new ConcurrentHashMap<>();

    public void insert(final String taskReferenceId, final ResultDto resultDto){
        CONCURRENT_MAP.putIfAbsent(taskReferenceId, resultDto);
    }

    public UploadFileResponse getUploadFileResponse(final String taskReferenceId){
        String message = "File is ready for download.";
        String fileDownloadUri = "";
        if(CONCURRENT_MAP.get(taskReferenceId) == null){
            message = "File is still under uploading phase. Please wait.";
            return new UploadFileResponse(message);
        }
        ResultDto resultDto = CONCURRENT_MAP.get(taskReferenceId);
        if(resultDto.httpStatus == HttpStatus.INTERNAL_SERVER_ERROR){
            message = resultDto.message;
        }else {
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(resultDto.storage == Storage.DB ? "/video/downloadFileFromMongo/" : "/video/downloadFileFromDirectory/")
                    .path(resultDto.objectId)
                    .toUriString();
        }

        UploadFileResponse uploadFileResponse = new UploadFileResponse(resultDto.objectId, fileDownloadUri,
                "application/octet-stream", resultDto.size, message);

        return uploadFileResponse;
    }

    public static class ResultDto{
        HttpStatus httpStatus;
        String message;
        String objectId;
        long size;
        Storage storage;
        String docId;

        public ResultDto(HttpStatus httpStatus, String message, String objectId, long size, Storage storage) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.objectId = objectId;
            this.size = size;
            this.storage = storage;
        }

        public ResultDto(HttpStatus httpStatus, String message, String objectId, long size, Storage storage, String docId) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.objectId = objectId;
            this.size = size;
            this.storage = storage;
            this.docId = docId;
        }

        public ResultDto(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }
    }

    public enum Storage{
        DB, FILE;
    }
}
