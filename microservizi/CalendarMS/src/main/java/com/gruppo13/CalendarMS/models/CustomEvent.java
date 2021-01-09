package com.gruppo13.CalendarMS.models;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
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
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "EVENT_TYPE")
    private eventType type;

    @Column(name = "IS_SYNCH")
    private boolean isSync;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="working_group_id")
    private WorkingGroup workingGroup;

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

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
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
}
