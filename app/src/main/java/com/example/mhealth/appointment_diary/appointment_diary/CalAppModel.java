package com.example.mhealth.appointment_diary.appointment_diary;

public class CalAppModel {

    String appointmenttype, appointmentdate;
    int Nos;

    public CalAppModel() {
    }

    public CalAppModel(String appointmenttype, String appointmentdate, int nos) {
        this.appointmenttype = appointmenttype;
        this.appointmentdate = appointmentdate;
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

    public int getNos() {
        return Nos;
    }

    public void setNos(int nos) {
        Nos = nos;
    }
}
