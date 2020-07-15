package com.mhealth.nishauri.Models;

public class PendingAppointment {

    private int id;
    private String appntmnt_date;
    private String app_type;
    private String approval_status;
    private String book_type;
    private String reason;
    private String comments;
    private String user;
    private String book_id;
    public boolean expanded = false;
    public boolean parent = false;

    public PendingAppointment(int id, String appntmnt_date, String app_type, String approval_status, String book_type, String reason, String comments, String user, String book_id) {

        this.id = id;
        this.appntmnt_date = appntmnt_date;
        this.app_type = app_type;
        this.approval_status = approval_status;
        this.book_type = book_type;
        this.reason = reason;
        this.comments = comments;
        this.user = user;
        this.book_id = book_id;

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

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(String approval_status) {
        this.approval_status = approval_status;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }




}
