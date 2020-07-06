package com.mhealth.nishauri.Models;

public class PreviousAppointment {

    private String appntmnt_date;
    private String app_status;
    private String visit_type;

    public PreviousAppointment(String appntmnt_date, String app_status, String visit_type) {

        this.appntmnt_date = appntmnt_date;
        this.app_status = app_status;
        this.visit_type = visit_type;

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

}
