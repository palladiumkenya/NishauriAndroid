package com.example.mhealth.appointment_diary.defaulters_diary.missed.visit;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class MissedVisitModel {


    String ccnumber,thename,phone,apptype,date,read,patientID,fileserial,informantnumber,lastdateread,readcount;

    public MissedVisitModel() {
    }

    public MissedVisitModel(String ccnumber, String thename, String phone, String apptype, String date,String read,String patientID,String fileserial,String informantnumber,String lastdateread,String readcount) {
        this.ccnumber = ccnumber;
        this.thename = thename;
        this.phone = phone;
        this.apptype = apptype;
        this.date = date;
        this.read = read;
        this.patientID=patientID;
        this.fileserial=fileserial;
        this.informantnumber=informantnumber;
        this.readcount=readcount;
        this.lastdateread=lastdateread;
    }

    public String getLastdateread() {
        return lastdateread;
    }

    public void setLastdateread(String lastdateread) {
        this.lastdateread = lastdateread;
    }

    public String getReadcount() {
        return readcount;
    }

    public void setReadcount(String readcount) {
        this.readcount = readcount;
    }

    public String getInformantnumber() {
        return informantnumber;
    }

    public void setInformantnumber(String informantnumber) {
        this.informantnumber = informantnumber;
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

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
