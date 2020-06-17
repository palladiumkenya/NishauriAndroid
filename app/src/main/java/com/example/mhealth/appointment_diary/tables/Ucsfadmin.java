package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Ucsfadmin extends SugarRecord {

    String uname,idnumber;

    public Ucsfadmin() {
    }

    public Ucsfadmin(String uname, String idnumber) {
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
