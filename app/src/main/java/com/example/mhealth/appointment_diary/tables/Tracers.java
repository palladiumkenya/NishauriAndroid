package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Tracers extends SugarRecord {

    public String fname,mname,phoneno,userid;

    public Tracers() {
    }

    public Tracers(String fname, String mname, String phoneno, String userid) {
        this.fname = fname;
        this.mname = mname;
        this.phoneno = phoneno;
        this.userid = userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
