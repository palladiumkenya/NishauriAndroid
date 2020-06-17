package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Assignedtracers extends SugarRecord {

    String username,appointmentid;

    public Assignedtracers() {
    }

    public Assignedtracers(String username, String appointmentid) {
        this.username = username;
        this.appointmentid = appointmentid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }
}
