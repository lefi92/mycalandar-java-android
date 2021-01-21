package com.example.mycalandar;

public class EventInvit {
    private String id;
    private String eventId;
    private String emailInvit;
    private String title;
    private String description;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;
    private String organiseBy;

    public EventInvit() {
    }

    public EventInvit(String id, String eventId, String emailInvit,String title, String description,int day, int month, int year,int hour,int min,String organiseBy) {
        this.id = id;
        this.eventId = eventId;
        this.emailInvit = emailInvit;
        this.title = title;
        this.description = description;
        this.day = day;
        this.year = year;
        this.month = month;
        this.hour = hour;
        this.min = min;
        this.organiseBy = organiseBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getOrganiseBy() {
        return organiseBy;
    }

    public void setOrganiseBy(String organiseBy) {
        this.organiseBy = organiseBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEmailInvit() {
        return emailInvit;
    }

    public void setEmailInvit(String emailInvit) {
        this.emailInvit = emailInvit;
    }
}
