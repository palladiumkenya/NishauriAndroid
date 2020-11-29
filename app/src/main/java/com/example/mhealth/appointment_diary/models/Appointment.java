package com.example.mhealth.appointment_diary.models;

public class Appointment {
    private int appointment_id;
    private  String f_name;
    private String m_name;
    private String l_name;
    private String clinic_number;
    private String hei_no;
    private String clinic;
    private String appointment_date;
    private String appointment_type;


    public Appointment(int appointment_id, String f_name, String m_name, String l_name, String clinic_number, String hei_no, String clinic, String appointment_date, String appointment_type) {
        this.appointment_id = appointment_id;
        this.f_name = f_name;
        this.m_name = m_name;
        this.l_name = l_name;
        this.clinic_number = clinic_number;
        this.hei_no = hei_no;
        this.clinic = clinic;
        this.appointment_date = appointment_date;
        this.appointment_type = appointment_type;
    }


    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getClinic_number() {
        return clinic_number;
    }

    public void setClinic_number(String clinic_number) {
        this.clinic_number = clinic_number;
    }

    public String getHei_no() {
        return hei_no;
    }

    public void setHei_no(String hei_no) {
        this.hei_no = hei_no;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }
}
