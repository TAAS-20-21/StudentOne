package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(	name = "Student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Student extends Person implements Serializable {
    private static final long serialVersionUID = -4968085150152136222L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Student(String name) {
        this.setName(name);
    }

    public Student() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
