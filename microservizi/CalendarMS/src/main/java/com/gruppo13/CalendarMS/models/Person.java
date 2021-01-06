package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public  abstract  class Person implements Serializable {
    private static final long serialVersionUID = 2220251204130856463L;

    private String email;

    private String password;

    @Column(name = "NAME")
    private String name;  //da mettere private ma così facendo Docente non eredita attributo

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "CALENDAR_KEY", unique = true)
    private String calendar_key;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
