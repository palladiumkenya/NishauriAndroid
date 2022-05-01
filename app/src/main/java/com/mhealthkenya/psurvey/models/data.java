package com.mhealthkenya.psurvey.models;

public class data {
    private int id;
    private String participant;

    public data() {
    }


    public data(int id, String participant) {
        this.id = id;
        this.participant = participant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
