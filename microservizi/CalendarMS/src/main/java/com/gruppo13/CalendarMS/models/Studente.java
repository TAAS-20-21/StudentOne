package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(	name = "Studente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Studente extends Persona implements Serializable {
    private static final long serialVersionUID = -4968085150152136222L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Studente(String name) {
        this.setName(name);
    }

    public Studente() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
