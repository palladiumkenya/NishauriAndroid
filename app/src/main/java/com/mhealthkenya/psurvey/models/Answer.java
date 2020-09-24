package com.mhealthkenya.psurvey.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer extends ArrayList implements Serializable {

    private int id;
    private String option;
    private String created_at;
    private int question;
    private int created_by;

    public Answer(int id, String option, String created_at, int question, int created_by){

        this.id = id;
        this.option = option;
        this.question = question;
        this.created_at = created_at;
        this.created_by = created_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

}
