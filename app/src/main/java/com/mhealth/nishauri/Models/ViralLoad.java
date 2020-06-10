package com.mhealth.nishauri.Models;

public class ViralLoad {

    private int id;
    private String r_id;
    private String result_type;
    private String result_content;
    private String date_collected;
    private String lab_name;
    private int user;


    public ViralLoad(int id, String r_id, String result_type, String result_content, String date_collected, String lab_name, int user) {
        this.id = id;
        this.r_id = r_id;
        this.result_type = result_type;
        this.result_content = result_content;
        this.date_collected = date_collected;
        this.lab_name = lab_name;
        this.user = user;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getResult_content() {
        return result_content;
    }

    public void setResult_content(String result_content) {
        this.result_content = result_content;
    }

    public String getDate_collected() {
        return date_collected;
    }

    public void setDate_collected(String date_collected) {
        this.date_collected = date_collected;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
