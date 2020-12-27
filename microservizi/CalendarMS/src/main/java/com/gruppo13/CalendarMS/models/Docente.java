package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(	name = "Docente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Docente extends Persona implements Serializable {

    private static final long serialVersionUID = -7103857371480503611L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Docente(){}

    public Docente(String name) {
        this.setName(name);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
