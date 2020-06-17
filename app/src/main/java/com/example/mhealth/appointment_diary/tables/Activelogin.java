package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Activelogin extends SugarRecord {

    String uname;

    public Activelogin() {
    }

    public Activelogin(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
