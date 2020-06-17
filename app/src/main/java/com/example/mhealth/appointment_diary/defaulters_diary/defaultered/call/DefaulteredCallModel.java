package com.example.mhealth.appointment_diary.defaulters_diary.defaultered.call;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class DefaulteredCallModel {

    String ccnumber,thename,phone,apptype,date,read,patientid,fileserial,informantnumber,lastdateread,readcount;

    public DefaulteredCallModel() {
    }

    public DefaulteredCallModel(String ccnumber, String thename, String phone, String apptype, String date,String read,String patientid,String fileserial,String informantnumber,String lastdateread,String readcount) {
        this.ccnumber = ccnumber;
        this.thename = thename;
        this.phone = phone;
        this.apptype = apptype;
        this.date = date;
        this.read = read;
        this.patientid = patientid;
        this.fileserial=fileserial;
        this.informantnumber=informantnumber;
        this.lastdateread=lastdateread;
        this.readcount=readcount;

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

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }
}
