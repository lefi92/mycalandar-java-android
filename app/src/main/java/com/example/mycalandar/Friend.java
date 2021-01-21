package com.example.mycalandar;

public class Friend {
    private String id;
    private String invitBy;
    private String invited;
    private int accpeted;

    public Friend() {
    }

    public Friend(String id, String invitBy, String invited, int accpeted) {
        this.id = id;
        this.invitBy = invitBy;
        this.invited = invited;
        this.accpeted = accpeted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitBy() {
        return invitBy;
    }

    public void setInvitBy(String invitBy) {
        this.invitBy = invitBy;
    }

    public String getInvited() {
        return invited;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }

    public int getAccpeted() {
        return accpeted;
    }

    public void setAccpeted(int accpeted) {
        this.accpeted = accpeted;
    }
}
