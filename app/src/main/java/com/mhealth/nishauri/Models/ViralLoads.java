package com.mhealth.nishauri.Models;

public class ViralLoads {
    private String date_collected;
    private String result_content;

    public ViralLoads(String date_collected,String result_content) {
        this.date_collected = date_collected;
        this.result_content = result_content;
    }

    public String getDate_collected() {
        return date_collected;
    }

    public void setDate_collected(String date_collected) {
        this.date_collected = date_collected;
    }

    public String getResult_content() {
        return result_content;
    }

    public void setResult_content(String result_content) {
        this.result_content = result_content;
    }
}
