package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Mflcode extends SugarRecord {

    String mfl;

    public Mflcode() {
    }

    public Mflcode(String mfl) {
        this.mfl = mfl;
    }

    public String getMfl() {
        return mfl;
    }

    public void setMfl(String mfl) {
        this.mfl = mfl;
    }
}
