package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(	name = "Evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = -8168051983099495603L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
