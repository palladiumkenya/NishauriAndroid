package com.example.mhealth.appointment_diary.appointment_diary;

public class appType {
    int Nos;
    String appointmenttype, appointmentdate;

    public appType() {
    }

    public int getNos() {
        return Nos;
    }

    public void setNos(int nos) {
        Nos = nos;
    }

    public String getAppointmenttype() {
        return appointmenttype;
    }

    public void setAppointmenttype(String appointmenttype) {
        this.appointmenttype = appointmenttype;
    }

    public String getAppointmentdate() {
        return appointmentdate;
    }

    public void setAppointmentdate(String appointmentdate) {
        this.appointmentdate = appointmentdate;
    }
}
