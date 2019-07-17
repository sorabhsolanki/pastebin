package com.pastebin.repository;

import com.pastebin.entity.ProjectPropertyEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.TypedQuery;
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
public class ProjectPropertyRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ProjectPropertyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ProjectPropertyEntity> getAll(boolean isActive){

        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProjectPropertyEntity> criteriaQuery = builder.createQuery(ProjectPropertyEntity.class);
        Root<ProjectPropertyEntity> myObjectRoot = criteriaQuery.from(ProjectPropertyEntity.class);
        criteriaQuery.select(myObjectRoot).where(builder.equal(myObjectRoot.get("active"), isActive));

        Query<ProjectPropertyEntity> query =session.createQuery(criteriaQuery);
        List<ProjectPropertyEntity> list = query.getResultList();
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
