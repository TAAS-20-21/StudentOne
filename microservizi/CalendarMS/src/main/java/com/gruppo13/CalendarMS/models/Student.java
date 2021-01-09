package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "course_like",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> likedCourses;

    @ManyToMany(mappedBy = "members")
    private Set<WorkingGroup> workingGroups;


    public Student(Student student) {
        this.setName(student.getName());
        this.setEmail(student.getEmail());
        this.setPassword(student.getPassword());
        this.setSurname(student.getSurname());
        this.setLikedCourses(student.getLikedCourses());
    }

    public Student() {}

    public Set<Course> getLikedCourses() {
        return likedCourses;
    }

    public void setLikedCourses(Set<Course> likedCourses) {
        this.likedCourses = likedCourses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
