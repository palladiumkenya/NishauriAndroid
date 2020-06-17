package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Myaffiliation extends SugarRecord {

    String aff;

    public Myaffiliation() {
    }

    public Myaffiliation(String aff) {
        this.aff = aff;
    }

    public String getAff() {
        return aff;
    }

    public void setAff(String aff) {
        this.aff = aff;
    }
}
