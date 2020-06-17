package com.example.mhealth.appointment_diary.models;

import com.orm.SugarRecord;

/**
 * Created by abdullahi on 6/18/2018.
 */

public class AppointmentCounter extends SugarRecord {

    String mycounter;

    public AppointmentCounter() {
    }

    public AppointmentCounter(String mycounter) {
        this.mycounter = mycounter;
    }

    public void setMycounter(String mycounter) {
        this.mycounter = mycounter;
    }

    public String getMycounter() {
        return mycounter;
    }
}
