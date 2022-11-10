package com.example.mhealth.appointment_diary.models;

public class wards {

    int id;
    String name;
    int scounty_id;

    public wards(int id, String name, int scounty_id) {
        this.id = id;
        this.name = name;
        this.scounty_id = scounty_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScounty_id() {
        return scounty_id;
    }

    public void setScounty_id(int scounty_id) {
        this.scounty_id = scounty_id;
    }
}
