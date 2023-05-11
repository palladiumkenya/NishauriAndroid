package com.mhealth.nishauri.Models;

public class ViralLoad {


    String result, status, date;
    int plot;

    public ViralLoad(String result, String status, String date, int plot) {
        this.result = result;
        this.status = status;
        this.date = date;
        this.plot = plot;
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

    public int getPlot() {
        return plot;
    }

    public void setPlot(int plot) {
        this.plot = plot;
    }
}
