package com.mhealth.nishauri.Models;

public class PreviousArt {

    private int id;
    private String Regiment;
    private String date_started;
    private String user;
    private String end_date;

    public PreviousArt(int id,String Regiment, String date_started,String user,String end_date) {
        this.id = id;
        this.Regiment = Regiment;
        this.date_started = date_started;
        this.user = user;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegiment() {
        return Regiment;
    }

    public void setRegiment(String Regiment) {
        this.Regiment = Regiment;
    }

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

}
