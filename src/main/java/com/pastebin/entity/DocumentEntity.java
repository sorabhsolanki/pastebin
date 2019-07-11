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

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "bookmark_url")
    private String bookmarkUrl;

    @Column(name = "title")
    private String title;


    @Column(name = "directory_location")
    private String directoryPath;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "")
    private UrlEntity uri;

    public DocumentEntity() {
    }

    public DocumentEntity(String documentId, String fileType, long fileSize, String fileExtension, String fileName,
                          String directoryPath) {
        this.documentId = documentId;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.directoryPath = directoryPath;
    }

    public DocumentEntity(String documentId, String documentText, String title, String bookmarkUrl){
        this.documentId = documentId;
        this.documentText = documentText;
        this.title = title;
        this.bookmarkUrl = bookmarkUrl;
    }

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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBookmarkUrl() {
        return bookmarkUrl;
    }

    public void setBookmarkUrl(String bookmarkUrl) {
        this.bookmarkUrl = bookmarkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
