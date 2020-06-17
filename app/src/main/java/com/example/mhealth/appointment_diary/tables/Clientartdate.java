package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Clientartdate extends SugarRecord {

    String mfl,upn,artdate;

    public Clientartdate() {
    }

    public Clientartdate(String mfl, String upn, String artdate) {
        this.mfl = mfl;
        this.upn = upn;
        this.artdate = artdate;
    }

    public String getMfl() {
        return mfl;
    }

    public void setMfl(String mfl) {
        this.mfl = mfl;
    }

    public String getUpn() {
        return upn;
    }

    public void setUpn(String upn) {
        this.upn = upn;
    }

    public String getArtdate() {
        return artdate;
    }

    public void setArtdate(String artdate) {
        this.artdate = artdate;
    }
}
