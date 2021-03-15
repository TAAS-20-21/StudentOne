package com.example.studentoneapp;

public class Course {
    private Long id;

    private String name;

    private Integer CFU;

    private String info;

    private Integer lesson_hours;

    public Course() {}

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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", CFU=" + CFU +
                ", info='" + info + '\'' +
                ", lesson_hours=" + lesson_hours +
                '}';
    }
}
