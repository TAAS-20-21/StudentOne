package com.gruppo13.CalendarMS.util;

import com.gruppo13.CalendarMS.models.CustomEvent;

import java.util.Date;

public class ModifierObject {
    private Long id;
    private String str;
    private Date startDate;
    private  Date oldStartDate;
    private Date endDate;
    private Date oldEndDate;
    private CustomEvent.eventType eventType;
    private Long newId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public CustomEvent.eventType getEventType() {
        return eventType;
    }

    public void setEventType(CustomEvent.eventType eventType) {
        this.eventType = eventType;
    }

    public Long getNewId() {
        return newId;
    }

    public void setNewId(Long newId) {
        this.newId = newId;
    }

    public Date getOldStartDate() {
        return oldStartDate;
    }

    public void setOldStartDate(Date oldStartDate) {
        this.oldStartDate = oldStartDate;
    }

    public Date getOldEndDate() {
        return oldEndDate;
    }

    public void setOldEndDate(Date oldEndDate) {
        this.oldEndDate = oldEndDate;
    }
}
