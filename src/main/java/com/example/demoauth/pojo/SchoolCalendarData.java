package com.example.demoauth.pojo;

public class SchoolCalendarData {
    private String id;
    private String eventType;

    private String eventName;

    private String dateOfStartEvent;

    private String dateOfFinishEvent;

    private String eventColor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateOfStartEvent() {
        return dateOfStartEvent;
    }

    public void setDateOfStartEvent(String dateOfStartEvent) {
        this.dateOfStartEvent = dateOfStartEvent;
    }

    public String getDateOfFinishEvent() {
        return dateOfFinishEvent;
    }

    public void setDateOfFinishEvent(String dateOfFinishEvent) {
        this.dateOfFinishEvent = dateOfFinishEvent;
    }

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }
}
