package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Trccheck extends SugarRecord {

    String istracer;

    public Trccheck(String istracer) {

        this.istracer = istracer;
    }

    public String getIstracer() {

        return istracer;

    }

    public void setIstracer(String istracer) {

        this.istracer = istracer;

    }
}
