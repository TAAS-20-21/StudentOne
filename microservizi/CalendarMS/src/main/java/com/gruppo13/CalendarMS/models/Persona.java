package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public  abstract  class Persona implements Serializable {
    private static final long serialVersionUID = 2220251204130856463L;

<<<<<<< HEAD
    private String nome;

    private String cognome;

=======
>>>>>>> 19a3a1254ef25bf7ccc5bbf94a3bb780dc616347
    private String email;

    private String password;

    @Column(name = "NAME")
    private String name;  //da mettere private ma così facendo Docente non eredita attributo

    @Column(name = "SURNAME")
    private String surname;

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
