package com.example.mhealth.appointment_diary.models;

public class Hei {
    private  String hei_no;
    private  String hei_dob;
    private  String hei_first_name;
    private  String hei_middle_name;
    private  String hei_last_name;
    private  String pcr_week6;


    public Hei(String hei_no, String hei_dob, String hei_first_name, String hei_middle_name, String hei_last_name, String pcr_week6) {
        this.hei_no = hei_no;
        this.hei_dob = hei_dob;
        this.hei_first_name = hei_first_name;
        this.hei_middle_name = hei_middle_name;
        this.hei_last_name = hei_last_name;
        this.pcr_week6 = pcr_week6;
    }

    public String getHei_no() {
        return hei_no;
    }

    public void setHei_no(String hei_no) {
        this.hei_no = hei_no;
    }

    public String getHei_dob() {
        return hei_dob;
    }

    public void setHei_dob(String hei_dob) {
        this.hei_dob = hei_dob;
    }

    public String getHei_first_name() {
        return hei_first_name;
    }

    public void setHei_first_name(String hei_first_name) {
        this.hei_first_name = hei_first_name;
    }

    public String getHei_middle_name() {
        return hei_middle_name;
    }

    public void setHei_middle_name(String hei_middle_name) {
        this.hei_middle_name = hei_middle_name;
    }

    public String getHei_last_name() {
        return hei_last_name;
    }

    public void setHei_last_name(String hei_last_name) {
        this.hei_last_name = hei_last_name;
    }

    public String getPcr_week6() {
        return pcr_week6;
    }

    public void setPcr_week6(String pcr_week6) {
        this.pcr_week6 = pcr_week6;
    }
}
