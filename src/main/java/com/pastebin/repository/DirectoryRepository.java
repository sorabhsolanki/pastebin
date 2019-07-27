package com.pastebin.repository;

import com.pastebin.entity.DirectoryEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for doing DB operation on table 'directory'
 */
@Repository
@Transactional
public class DirectoryRepository {


    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;


    /*
     method for get all directory locations from DB, these location will be used for holding files.
     */
    public List<DirectoryEntity> getAll(){


        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DirectoryEntity> criteriaQuery = builder.createQuery(DirectoryEntity.class);

        Root<DirectoryEntity> variableRoot = criteriaQuery.from(DirectoryEntity.class);
        criteriaQuery.select(variableRoot);

        TypedQuery<DirectoryEntity> query = em.createQuery(criteriaQuery);
        List<DirectoryEntity> list = query.getResultList();
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
