package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Registrationtable extends SugarRecord{

    //public String username,password,sechint, secans,affiliation,phone;
    public String username,password,sechint, secans,phone;

    public Registrationtable() {
    }

    // public Registrationtable(String username, String password, String sechint,String secans,String affiliation,String phone) {
    public Registrationtable(String username, String password, String sechint,String secans,String phone) {
        this.username = username;
        this.password = password;
        this.sechint = sechint;
        this.secans =secans;
        // this.affiliation=affiliation;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   /* public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }*/

    public String getSecans() {
        return secans;
    }

    public void setSecans(String secans) {
        this.secans = secans;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSechint() {
        return sechint;
    }

    public void setSechint(String sechint) {
        this.sechint = sechint;
    }
}
