package com.mhealth.nishauri.Models;


import java.io.Serializable;

public class Dependant implements Serializable {

    private int id;
    private String first_name;
    private String surname;
    private String heiNumber;
    private String dob;
    private String approved;
    private int user;


    public Dependant(int id, String first_name, String surname, String heiNumber, String dob, String approved, int user) {
        this.id = id;
        this.first_name = first_name;
        this.surname = surname;
        this.heiNumber = heiNumber;
        this.dob = dob;
        this.approved = approved;
        this.user = user;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getHeiNumber() {
        return heiNumber;
    }

    public void setHeiNumber(String heiNumber) {
        this.heiNumber = heiNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
