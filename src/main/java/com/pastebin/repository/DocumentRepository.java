package com.pastebin.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository class for doing DB operations on 'document' table.
 */
@Repository
@Transactional
public class DocumentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public DocumentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
