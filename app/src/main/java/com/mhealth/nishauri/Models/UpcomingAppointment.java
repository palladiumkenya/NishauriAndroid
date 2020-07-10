package com.mhealth.nishauri.Models;

public class UpcomingAppointment {

    private int id;
    private String aid;
    private String appntmnt_date;
    private String app_status;
    private String visit_type;
    private String app_type;
    private String owner;
    private String dependant;
    private String created_at;
    private String updated_at;
    private String user;
    public boolean expanded = false;
    public boolean parent = false;

    public UpcomingAppointment(int id,String aid,String appntmnt_date, String app_status, String visit_type, String app_type, String owner, String dependant, String created_at, String updated_at, String user) {

        this.id = id;
        this.aid = aid;
        this.appntmnt_date = appntmnt_date;
        this.app_status = app_status;
        this.visit_type = visit_type;
        this.app_type = app_type;
        this.owner = owner;
        this.dependant = dependant;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAppntmnt_date() {
        return appntmnt_date;
    }

    public void setAppntmnt_date(String appntmnt_date) {
        this.appntmnt_date = appntmnt_date;
    }

    public String getApp_status() {
        return app_status;
    }

    public void setApp_status(String app_status) {
        this.app_status = app_status;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(String visit_type) {
        this.visit_type = visit_type;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDependant() {
        return dependant;
    }

    public void setDependant(String dependant) {
        this.dependant = dependant;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
