package com.pastebin.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 */
@Entity
@Table(name = "url")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tiny_uri")
    private String uri;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "uri")
    private Set<DocumentEntity> documentEntitySet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<DocumentEntity> getDocumentEntitySet() {
        return documentEntitySet;
    }

    public void setDocumentEntitySet(Set<DocumentEntity> documentEntitySet) {
        this.documentEntitySet = documentEntitySet;
    }
}
