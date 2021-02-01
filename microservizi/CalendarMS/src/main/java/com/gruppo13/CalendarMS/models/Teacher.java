package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(	name = "Teacher",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Teacher extends Person implements Serializable {

    private static final long serialVersionUID = -7103857371480503611L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "assigned_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> assignedCourses;

    public Teacher(){}

    public Teacher(Teacher teacher) {
        this.setName(teacher.getName());
        this.setEmail(teacher.getEmail());
        this.setPassword(teacher.getPassword());
        this.setSurname(teacher.getSurname());
        this.setAssignedCourses(teacher.getAssignedCourses());
    }

    public Set<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(Set<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
