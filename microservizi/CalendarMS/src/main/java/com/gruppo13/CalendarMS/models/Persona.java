package com.gruppo13.CalendarMS.models;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public  abstract  class Persona implements Serializable {
    private static final long serialVersionUID = 2220251204130856463L;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
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
