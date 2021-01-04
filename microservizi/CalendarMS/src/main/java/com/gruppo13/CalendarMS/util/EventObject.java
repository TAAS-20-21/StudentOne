package com.gruppo13.CalendarMS.util;

import com.google.api.client.util.DateTime;

public class EventObject {
    private String summary = "Undefined";
    private String location = "Undefined";
    private String description = "Undefined";
    private DateTime startDateTime = null;
    private DateTime endDateTime = null;


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
}
