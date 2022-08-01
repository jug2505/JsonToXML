package com.service.rest.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Document {
    private @Id @GeneratedValue Long id;
    private @Column(length = 4) String series;
    private @Column(length = 6) String number;
    private @Enumerated(EnumType.STRING) DocumentType type;
    private Date issueDate;

    public Document() {
    }

    public Document(Long id, String series, String number, DocumentType type, Date issueDate) {
        this.id = id;
        this.series = series;
        this.number = number;
        this.type = type;
        this.issueDate = issueDate;
    }

    public Document(String series, String number, DocumentType type, Date issueDate) {
        this.series = series;
        this.number = number;
        this.type = type;
        this.issueDate = issueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String documentSeries) {
        this.series = documentSeries;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String documentNumber) {
        this.number = documentNumber;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType documentType) {
        this.type = documentType;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", series='" + series + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", issueDate=" + issueDate +
                '}';
    }
}
