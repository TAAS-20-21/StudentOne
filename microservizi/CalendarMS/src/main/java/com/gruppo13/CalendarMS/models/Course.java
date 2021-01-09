package com.gruppo13.CalendarMS.models;

import javax.persistence.*;
import com.google.common.primitives.UnsignedInteger;
import jdk.jfr.Unsigned;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(	name = "Course")
public class Course implements Serializable{
    private static final long serialVersionUID = -3614185391977163873L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CFU")
    private Integer CFU;

    @Column(name = "INFO")
    @Lob
    private String info;

    @Column(name = "LESSON_HOURS")
    private Integer lesson_hours;

    @ManyToMany(mappedBy = "likedCourses")
    private Set<Student> likes;

    @ManyToMany(mappedBy = "assignedCourses")
    private Set<Teacher> assign_teachers;

    @OneToMany(mappedBy="course")
    private Set<CustomEvent> lesson;

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCFU() {
        return CFU;
    }

    public void setCFU(Integer CFU) {
        this.CFU = CFU;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getLesson_hours() {
        return lesson_hours;
    }

    public void setLesson_hours(Integer lesson_hours) {
        this.lesson_hours = lesson_hours;
    }
}
