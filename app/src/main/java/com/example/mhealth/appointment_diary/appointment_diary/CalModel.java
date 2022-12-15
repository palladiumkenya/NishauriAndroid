package com.example.mhealth.appointment_diary.appointment_diary;

public class CalModel {

    String clinic_no,client_name,client_phone_no,appointment_type,appntmnt_date,file_no,appointment_status,notification;

    public CalModel() {
    }

    public CalModel(String clinic_no, String client_name, String client_phone_no, String appointment_type, String appntmnt_date, String file_no, String appointment_status, String notification) {
        this.clinic_no = clinic_no;
        this.client_name = client_name;
        this.client_phone_no = client_phone_no;
        this.appointment_type = appointment_type;
        this.appntmnt_date = appntmnt_date;
        this.file_no = file_no;
        this.appointment_status = appointment_status;
        this.notification=notification;
    }

    public String getClinic_no() {
        return clinic_no;
    }

    public void setClinic_no(String clinic_no) {
        this.clinic_no = clinic_no;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_phone_no() {
        return client_phone_no;
    }

    public void setClient_phone_no(String client_phone_no) {
        this.client_phone_no = client_phone_no;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getAppntmnt_date() {
        return appntmnt_date;
    }

    public void setAppntmnt_date(String appntmnt_date) {
        this.appntmnt_date = appntmnt_date;
    }

    public String getFile_no() {
        return file_no;
    }

    public void setFile_no(String file_no) {
        this.file_no = file_no;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
