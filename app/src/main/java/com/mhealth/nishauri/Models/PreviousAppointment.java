package com.mhealth.nishauri.Models;

public class PreviousAppointment {

    private int id;
    private String appntmnt_date;
    private String app_status;
    private String visit_type;
    private String app_type;

    public PreviousAppointment(int id, String appntmnt_date, String app_status, String visit_type, String app_type) {

        this.id = id;
        this.appntmnt_date = appntmnt_date;
        this.app_status = app_status;
        this.visit_type = visit_type;
        this.app_type = app_type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
