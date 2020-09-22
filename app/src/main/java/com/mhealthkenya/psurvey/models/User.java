package com.mhealthkenya.psurvey.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String f_name;
    private String l_name;
    private String msisdn;
    private String email;
    private int facility;
    private int designation;

    public User(int id, String f_name,String l_name,String msisdn,String email, int facility,int designation) {

        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.msisdn = msisdn;
        this.email = email;
        this.facility = facility;
        this.designation = designation;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFacility() {
        return facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    public int getDesignation() {
        return designation;
    }

    public void setDesignation(int designation) {
        this.designation = designation;
    }

}
