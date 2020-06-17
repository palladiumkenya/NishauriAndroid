package com.example.mhealth.appointment_diary.models;

import com.orm.SugarRecord;

/**
 * Created by abdullahi on 11/12/2017.
 */


public class Appointments extends SugarRecord {

    String ccnumber;
    String name;
    String phone;
    String appointmenttype;
    String date;
    String read;
    String appointmentid;
    String traced;
    String fileserial;
    String informantnumber;
    String traceruname;
    String lastdateread;
    String readcount;


    public Appointments() {
    }

    public Appointments(String ccnumber, String name, String phone, String appointmenttype, String date, String read, String appointmentid, String traced,String fileserial,String informantnumber,String traceruname,String lastdateread,String readcount) {
        this.ccnumber = ccnumber;
        this.name = name;
        this.phone = phone;
        this.appointmenttype = appointmenttype;
        this.date = date;
        this.read = read;
        this.appointmentid = appointmentid;
        this.traced=traced;
        this.fileserial=fileserial;
        this.informantnumber=informantnumber;
        this.traceruname=traceruname;
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

    public String getTraceruname() {
        return traceruname;
    }

    public void setTraceruname(String traceruname) {
        this.traceruname = traceruname;
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

    public String getTraced() {
        return traced;
    }

    public void setTraced(String traced) {
        this.traced = traced;
    }

    public String getCcnumber() {
        return ccnumber;
    }

    public void setCcnumber(String ccnumber) {
        this.ccnumber = ccnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppointmenttype() {
        return appointmenttype;
    }

    public void setAppointmenttype(String appointmenttype) {
        this.appointmenttype = appointmenttype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRead(){
        return read;
    }

    public void setRead(String read){
        this.read = read;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }
}
