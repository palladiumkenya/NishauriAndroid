package com.example.mhealth.appointment_diary.tables;

public class urlModel {

    private int id;
    private String stage;
    private String url;


    public urlModel() {
    }

    public urlModel(int id, String stage, String url) {
        this.id = id;
        this.stage = stage;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
