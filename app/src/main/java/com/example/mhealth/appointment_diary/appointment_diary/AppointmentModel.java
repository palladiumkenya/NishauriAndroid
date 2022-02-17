package com.example.mhealth.appointment_diary.appointment_diary;

import java.util.List;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class AppointmentModel {


    String ccnumber,thename,phone,apptype,date,read,patientID,fileserial;
    //private List<AppointmentModel>  = new ArrayList<>();

    public AppointmentModel() {
    }

    public AppointmentModel(String ccnumber, String thename, String phone, String apptype, String date,String read,String patientID,String fileserial) {
        this.ccnumber = ccnumber;
        this.thename = thename;
        this.phone = phone;
        this.apptype = apptype;
        this.date = date;
        this.read = read;
        this.patientID=patientID;
        this.fileserial=fileserial;

    }

    public String getFileserial() {
        return fileserial;
    }

    public void setFileserial(String fileserial) {
        this.fileserial = fileserial;
    }

    public String getCcnumber() {
        return ccnumber;
    }

    public void setCcnumber(String ccnumber) {
        this.ccnumber = ccnumber;
    }

    public String getThename() {
        return thename;
    }

    public void setThename(String thename) {
        this.thename = thename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRead(String read){
        this.read = read;
    }
    public String getRead(){
        return read;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
