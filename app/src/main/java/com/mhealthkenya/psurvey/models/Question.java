package com.mhealthkenya.psurvey.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "QuestionsTable")
public class Question implements Serializable {
    @PrimaryKey@NonNull
    @ColumnInfo(name = "id")
    private int id;

    private String question;
    private int question_type;
    private String createdAt;
    private int questionnaire;
    private int created_by;
    private boolean is_required;

    public Question(int id, String question, int question_type, String createdAt, int questionnaire, int created_by, boolean is_required){

        this.id = id;
        this.question = question;
        this.createdAt = createdAt;
        this.question_type = question_type;
        this.questionnaire = questionnaire;
        this.created_by = created_by;
        this.is_required = is_required;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    public int getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(int questionnaire) {
        this.questionnaire = questionnaire;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public boolean isIs_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }
}
