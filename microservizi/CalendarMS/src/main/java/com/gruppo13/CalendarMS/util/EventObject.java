package com.gruppo13.CalendarMS.util;

import com.google.api.client.util.DateTime;
import com.gruppo13.CalendarMS.models.Course;
import com.gruppo13.CalendarMS.models.CustomEvent;
import com.gruppo13.CalendarMS.models.WorkingGroup;

import javax.persistence.Column;
import java.util.Date;

public class EventObject {

    private String summary = "Undefined";
    private String location = "Undefined";
    private String description = "Undefined";
    private DateTime startDateTime = null;
    private DateTime endDateTime = null;
    private WorkingGroup workingGroup;
    private Course course;
    private CustomEvent.eventType type;
    private Long angularId;
    private Date startRecur;
    private Date endRecur;
    private String daysOfWeek;
    private Long startTimeRecurrent;
    private Long endTimeRecurrent;

    public WorkingGroup getWorkingGroup() {
        return workingGroup;
    }

    public void setWorkingGroup(WorkingGroup workingGroupId) {
        this.workingGroup = workingGroupId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course courseId) {
        this.course = courseId;
    }

    public CustomEvent.eventType getType() {
        return type;
    }

    public void setType(CustomEvent.eventType type) {
        this.type = type;
    }

    public void setSummary(String summary) {
            this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
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
