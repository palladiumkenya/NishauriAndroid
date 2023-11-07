package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName ="AnswerEntity")
public class AnswerEntity {

    @PrimaryKey
    private int id;
    private  String option;
    private String createdAt;
    private int questionId;
    private int questionnaireId;
    private int createdBy;


    public AnswerEntity() {
    }

    public AnswerEntity(int id, String option, String createdAt, int questionId, int questionnaireId, int createdBy) {
        this.id = id;
        this.option = option;
        this.createdAt = createdAt;
        this.questionId = questionId;
        this.questionnaireId = questionnaireId;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
