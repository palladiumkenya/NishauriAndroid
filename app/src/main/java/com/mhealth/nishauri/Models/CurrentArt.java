package com.mhealth.nishauri.Models;

public class CurrentArt {

    private int id;
    private String Regiment;
    private String date_started;
    private String user;

    public CurrentArt(int id,String Regiment, String date_started,String user) {
        this.id = id;
        this.Regiment = Regiment;
        this.date_started = date_started;
        this.user = user;
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


}
