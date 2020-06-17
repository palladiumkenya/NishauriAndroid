package com.example.mhealth.appointment_diary.models;

import com.orm.SugarRecord;

/**
 * Created by abdullahi on 6/18/2018.
 */

public class RegisterCounter extends SugarRecord {
    String mycounter;

    public RegisterCounter() {
    }

    public RegisterCounter(String mycounter) {
        this.mycounter = mycounter;
    }

    public void setMycounter(String mycounter) {
        this.mycounter = mycounter;
    }

    public String getMycounter() {
        return mycounter;
    }
}
