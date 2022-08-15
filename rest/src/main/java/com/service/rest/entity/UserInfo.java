package com.service.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class UserInfo {
    @Id @GeneratedValue
    private  Long id;
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String surname;
    @Column(length = 32)
    private String patronymic;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id")
    private Document document;

    public UserInfo() {
    }

    public UserInfo(String name, String surname, String patronymic, Date birthDate, Gender gender, Document document) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.gender = gender;
        this.document = document;
    }

    @JsonIgnore
    public boolean isFieldsNull() {
        return name == null || surname == null || patronymic == null || birthDate == null || gender == null || document.isFieldsNull();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", document=" + document +
                '}';
    }
}
