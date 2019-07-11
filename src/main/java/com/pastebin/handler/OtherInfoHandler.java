package com.pastebin.handler;

import com.pastebin.dto.OtherInfoDto;
import com.pastebin.service.OtherInfoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class OtherInfoHandler {

    private final OtherInfoStorageService otherInfoStorageService;

    @Autowired
    public OtherInfoHandler(OtherInfoStorageService otherInfoStorageService) {
        this.otherInfoStorageService = otherInfoStorageService;
    }

    public String upload(final OtherInfoDto infoDto, final String docID) {
        return otherInfoStorageService.storeInfo(infoDto, docID);
    }
}
