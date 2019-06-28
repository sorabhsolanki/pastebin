package com.pastebin.entity;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity
@Table(name = "document")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "doc_text")
    private String documentText;

    @Column(name = "is_image")
    private boolean image;

    @Column(name = "is_file")
    private boolean file;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "directory_location")
    private String directoryPath;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "")
    private UrlEntity uri;

    @OneToOne
    @JoinColumn(name = "directory_id")
    private DirectoryEntity directoryEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentText() {
        return documentText;
    }

    public void setDocumentText(String documentText) {
        this.documentText = documentText;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean isFile() {
        return file;
    }

    public void setFile(boolean file) {
        this.file = file;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DirectoryEntity getDirectoryEntity() {
        return directoryEntity;
    }

    public void setDirectoryEntity(DirectoryEntity directoryEntity) {
        this.directoryEntity = directoryEntity;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public UrlEntity getUri() {
        return uri;
    }

    public void setUri(UrlEntity uri) {
        this.uri = uri;
    }
}
