package com.example.mycalandar;

import com.google.firebase.auth.FirebaseAuth;

public class event {
    private String id;
    private String uid;
    private String title;
    private String description;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;
   // FirebaseAuth auth;

    public event() {
    }

    public event(String id, String uid,String title, String description,int day, int month, int year,int hour,int min) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.day = day;
        this.year = year;
        this.month = month;
        this.hour = hour;
        this.min = min;
        this.uid = uid;
       // this.auth = auth;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setDesccription(String desccription) {
        this.description = desccription;
    }

   /* public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }*/
}
