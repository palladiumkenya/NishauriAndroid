package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Ucsftracers extends SugarRecord {


    String uname,idnumber;

    public Ucsftracers() {
    }

    public Ucsftracers(String uname, String idnumber) {
        this.uname = uname;
        this.idnumber = idnumber;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
