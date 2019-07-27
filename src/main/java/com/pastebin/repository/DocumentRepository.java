package com.pastebin.repository;

import com.pastebin.entity.DocumentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for doing DB operations on 'document' table.
 */
@Repository
@Transactional
public class DocumentRepository {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    public boolean isDocIdPresent(final String docID){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DocumentEntity> criteriaQuery = builder.createQuery(DocumentEntity.class);

        Root<DocumentEntity> myObjectRoot = criteriaQuery.from(DocumentEntity.class);
        criteriaQuery.select(myObjectRoot).where(builder.equal(myObjectRoot.get("documentId"), docID));


        TypedQuery<DocumentEntity> query = em.createQuery(criteriaQuery);
        return query.getResultList() != null ? true : false;
    }

    public Optional<List<DocumentEntity>> getDocument(final String docID){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<DocumentEntity> criteriaQuery = builder.createQuery(DocumentEntity.class);

        Root<DocumentEntity> myObjectRoot = criteriaQuery.from(DocumentEntity.class);
        criteriaQuery.select(myObjectRoot).where(builder.equal(myObjectRoot.get("documentId"), docID));

        TypedQuery<DocumentEntity> query = em.createQuery(criteriaQuery);
        List<DocumentEntity> documentEntityList = query.getResultList();

        return Optional.of(documentEntityList);
    }

    public void update(DocumentEntity documentEntity){
        em.persist(documentEntity);
    }

    public void insert(DocumentEntity documentEntity){
        em.persist(documentEntity);
    }


}
