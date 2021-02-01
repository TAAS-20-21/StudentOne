package com.gruppo13.CalendarMS.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * The persistent class for the user database table.
 *
 */
public class User implements Serializable {


    private static final long serialVersionUID = 65981149772133526L;

    private Long id;

    //private String providerUserId;

    private String email;

    private String name;

    private String surname;

    //private String provider;

    //private boolean isProfessor;

    //private String student_number_id;

    private Set<TypeRole> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
/*
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
*/
    public Set<TypeRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TypeRole> roles) {
        this.roles = roles;
    }
/*
    public String getStudent_number_id() {
        return student_number_id;
    }

    public void setStudent_number_id(String student_number_id) {
        this.student_number_id = student_number_id;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }*/
}