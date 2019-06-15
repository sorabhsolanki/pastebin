package com.pastebin.repository;

import com.pastebin.entity.DirectoryEntity;
import com.pastebin.entity.ProjectPropertyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 */
@Repository
@Transactional
public class DirectoryRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public DirectoryRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<DirectoryEntity> getAll(){

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DirectoryEntity> criteriaQuery = builder.createQuery(DirectoryEntity.class);
        Query<DirectoryEntity> query =session.createQuery(criteriaQuery);

        List<DirectoryEntity> list = query.getResultList();
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
