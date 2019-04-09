package com.pastebin.service;

import com.pastebin.entity.ProjectPropertyEntity;
import com.pastebin.repository.ProjectPropertyRepository;
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
public class ProjectPropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectPropertyService.class);

    private final ProjectPropertyRepository projectPropertyRepository;

    @Autowired
    public ProjectPropertyService(ProjectPropertyRepository projectPropertyRepository) {
        this.projectPropertyRepository = projectPropertyRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProjectPropertyEntity> getAllActiveProjectProperties(){
        LOG.info("Retrieving all active project properties.");
        return projectPropertyRepository.getAll(true);
    }
}
