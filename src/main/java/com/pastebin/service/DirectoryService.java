package com.pastebin.service;

import com.pastebin.entity.DirectoryEntity;
import com.pastebin.repository.DirectoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 */
@Service
public class DirectoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectPropertyService.class);

    private final DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<DirectoryEntity> getAllDirectoryLocations(){
        LOG.info("Retrieving all directory locations.");
        return directoryRepository.getAll();
    }
}
