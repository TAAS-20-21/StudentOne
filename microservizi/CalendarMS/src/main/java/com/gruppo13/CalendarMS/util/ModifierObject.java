package com.gruppo13.CalendarMS.util;

import com.gruppo13.CalendarMS.models.CustomEvent;

import java.util.Date;

public class ModifierObject {
    private Long id;
    private String str;
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
}
