package com.example.studentoneapp;

public class CourseUserObject {
    private Long courseId;

    private Long personId;

    public CourseUserObject() {
    }

    public CourseUserObject(Long courseId, Long personId) {
        this.courseId = courseId;
        this.personId = personId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
