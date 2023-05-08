package com.mhealth.nishauri.Models;


import java.io.Serializable;

public class Dependant implements Serializable {

    public int dependant_age;
    public String moh_upi,  clinic_number, dependant_name;

    public Dependant(int dependant_age, String moh_upi, String clinic_number, String dependant_name) {
        this.dependant_age = dependant_age;
        this.moh_upi = moh_upi;
        this.clinic_number = clinic_number;
        this.dependant_name = dependant_name;
    }

    public int getDependant_age() {
        return dependant_age;
    }

    public void setDependant_age(int dependant_age) {
        this.dependant_age = dependant_age;
    }

    public String getMoh_upi() {
        return moh_upi;
    }

    public void setMoh_upi(String moh_upi) {
        this.moh_upi = moh_upi;
    }

    public String getClinic_number() {
        return clinic_number;
    }

    public void setClinic_number(String clinic_number) {
        this.clinic_number = clinic_number;
    }

    public String getDependant_name() {
        return dependant_name;
    }

    public void setDependant_name(String dependant_name) {
        this.dependant_name = dependant_name;
    }
}
