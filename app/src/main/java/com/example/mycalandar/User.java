package com.example.mycalandar;

public class User {
    String id;
    String Name;
    String Mail;
    String uid;

    public User() {
    }

    public User(String id,String name, String mail, String uid) {
        Name = name;
        Mail = mail;
        this.uid = uid;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
