package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class AnswerEntity {

    @PrimaryKey
    private int id;

    private  String option;
    private String createdAt;
    private long questionId;
    private int createdBy;


    public AnswerEntity() {
    }

    public AnswerEntity( String option, String createdAt, long questionId, int createdBy) {
        //this.id = id;
        this.option = option;
        this.createdAt = createdAt;
        this.questionId = questionId;
        this.createdBy = createdBy;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
