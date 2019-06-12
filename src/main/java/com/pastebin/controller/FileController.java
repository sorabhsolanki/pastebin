package com.pastebin.controller;

import com.pastebin.dto.FileStatusResponse;
import com.pastebin.handler.FileStorageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
        String fileReferenceId = fileStorageHandler.upload(file);
        String fileStatusUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/video/checkStatus/")
                .path(fileReferenceId)
                .toUriString();
        FileStatusResponse statusResponse = new FileStatusResponse(fileReferenceId, fileStatusUri,
                "application/octet-stream", "Request has been accepted.");
        return ResponseEntity.status(HttpStatus.CREATED).body(statusResponse);
    }

}
