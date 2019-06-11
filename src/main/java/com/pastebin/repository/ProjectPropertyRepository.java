package com.pastebin.repository;

import com.pastebin.entity.ProjectPropertyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 */
@Repository
@Transactional
public class ProjectPropertyRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ProjectPropertyRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProjectPropertyEntity> getAll(boolean isActive){
        TypedQuery<ProjectPropertyEntity> query = entityManager.createNamedQuery("ProjectPropertyEntity.findAll",
                ProjectPropertyEntity.class);
        query.setParameter("isActive", isActive);
        List<ProjectPropertyEntity> list = query.getResultList();
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
