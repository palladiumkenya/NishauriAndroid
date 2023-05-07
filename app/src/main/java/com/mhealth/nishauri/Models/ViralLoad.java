package com.mhealth.nishauri.Models;

public class ViralLoad {


    String result, status, date;

    public ViralLoad(String result, String status, String date) {
        this.result = result;
        this.status = status;
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
