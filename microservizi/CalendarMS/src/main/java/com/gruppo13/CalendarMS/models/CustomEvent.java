package com.gruppo13.CalendarMS.models;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "Event")
public class CustomEvent implements Serializable {

    private static final long serialVersionUID = -8168051983099495603L;

    public enum eventType {MEETING, LESSON, DUE_DATE};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOOGLE_ID")
    private String googleId;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "EVENT_TYPE")
    private eventType type;

    @Column(name = "START_RECUR_DATE")
    private Date startRecur;

    @Column(name = "END_RECUR_DATE")
    private Date endRecur;

    @Column(name = "DAYS_OF_WEEK", length=7)
    private String daysOfWeek;


    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;


    @ManyToOne
    @JoinColumn(name="working_group_id")
    private WorkingGroup workingGroup;

    @Column(name = "ANGULAR_ID")
    private Long angularId;

    @Column(name = "START_TIME_RECURRENT")
    private Long startTimeRecurrent;

    @Column(name = "END_TIME_RECURRENT")
    private Long endTimeRecurrent;

    public CustomEvent(){}

    public CustomEvent(CustomEvent customEvent){
        this.startTime = customEvent.getStartTime();
        this.googleId = customEvent.getGoogleId();
        this.endTime = customEvent.getEndTime();
        this.title = customEvent.getTitle();
        this.type = customEvent.getType();
        if(customEvent.getCourse() != null)
            this.course = customEvent.getCourse();
        else {
            this.workingGroup = customEvent.getWorkingGroup();
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public WorkingGroup getWorkingGroup() {
        return workingGroup;
    }

    public void setWorkingGroup(WorkingGroup workingGroup) {
        this.workingGroup = workingGroup;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public eventType getType() {
        return type;
    }

    public void setType(eventType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAngularId() { return angularId; }

    public void setAngularId(Long angularId) { this.angularId = angularId; }

    public Date getStartRecur() {
        return startRecur;
    }

    public void setStartRecur(Date startRecur) {
        this.startRecur = startRecur;
    }

    public Date getEndRecur() {
        return endRecur;
    }

    public void setEndRecur(Date endRecur) {
        this.endRecur = endRecur;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Long getStartTimeRecurrent() {
        return startTimeRecurrent;
    }

    public void setStartTimeRecurrent(Long startTimeRecurrent) {
        this.startTimeRecurrent = startTimeRecurrent;
    }

    public Long getEndTimeRecurrent() {
        return endTimeRecurrent;
    }

    public void setEndTimeRecurrent(Long endTimeRecurrent) {
        this.endTimeRecurrent = endTimeRecurrent;
    }
}
