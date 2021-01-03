package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(	name = "Teacher",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Teacher extends Person implements Serializable {

    private static final long serialVersionUID = -7103857371480503611L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Teacher(){}

    public Teacher(String name) {
        this.setName(name);
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    public long getId() {
        return id;
    }
}
