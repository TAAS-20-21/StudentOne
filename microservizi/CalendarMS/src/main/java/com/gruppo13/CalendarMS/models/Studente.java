package com.gruppo13.CalendarMS.models;

import com.gruppo13.models.Persona;
import org.apache.catalina.users.AbstractUser;

import javax.persistence.*;

@Entity
@Table(	name = "Studente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Studente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
