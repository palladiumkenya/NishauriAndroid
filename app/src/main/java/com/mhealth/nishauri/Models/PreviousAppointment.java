package com.mhealth.nishauri.Models;

public class PreviousAppointment {

    public String appointment_type, appointment_date, visit_date, appt_status;

    public PreviousAppointment(String appointment_type, String appointment_date, String visit_date, String appt_status) {
        this.appointment_type = appointment_type;
        this.appointment_date = appointment_date;
        this.visit_date = visit_date;
        this.appt_status = appt_status;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getAppt_status() {
        return appt_status;
    }

    public void setAppt_status(String appt_status) {
        this.appt_status = appt_status;
    }
}
