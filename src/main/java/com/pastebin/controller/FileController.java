package com.pastebin.controller;

import com.pastebin.dto.FileStatusResponse;
import com.pastebin.dto.UploadFileResponse;
import com.pastebin.handler.FileStorageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 */
@RestController
@RequestMapping("/paste/back/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    private final FileStorageHandler fileStorageHandler;

    @Autowired
    public FileController(FileStorageHandler fileStorageHandler) {
        this.fileStorageHandler = fileStorageHandler;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        LOG.info(String.format("Request received for upload file with file name %s", file.getOriginalFilename()));
        String referenceId = fileStorageHandler.upload(file);
        String fileStatusUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/checkStatus/")
                .path(referenceId)
                .toUriString();
        FileStatusResponse statusResponse = new FileStatusResponse(referenceId, fileStatusUri,
                "application/octet-stream", "Request has been accepted.");
        return ResponseEntity.status(HttpStatus.CREATED).body(statusResponse);
    }

    @RequestMapping(value = "/checkStatus/{referenceId}", method = RequestMethod.GET)
    public ResponseEntity<?> checkStatus(@PathVariable String referenceId) {
        LOG.info(String.format("Request received for check status for referenceId %s", referenceId));
        UploadFileResponse uploadFileResponse = fileStorageHandler.getUploadResponse(referenceId);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFileResponse);
    }
}
