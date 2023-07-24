package com.mhealth.nishauri.utils;

import androidx.annotation.NonNull;

public class Question {

    private int id;

    private String question;
    private int question_type;
    private String createdAt;
    private int questionnaire;
    private int created_by;
    private boolean is_required;
    private boolean is_repeatable;
    private String date_validation;

    public Question(int id, String question, int question_type, String createdAt, int questionnaire, int created_by, boolean is_required, String date_validation, boolean is_repeatable){

        this.id = id;
        this.question = question;
        this.createdAt = createdAt;
        this.question_type = question_type;
        this.questionnaire = questionnaire;
        this.created_by = created_by;
        this.is_required = is_required;
        this.date_validation=date_validation;
        this.is_repeatable= is_repeatable;

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

    public String getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(String date_validation) {
        this.date_validation = date_validation;
    }

    public boolean isIs_repeatable() {
        return is_repeatable;
    }

    public void setIs_repeatable(boolean is_repeatable) {
        this.is_repeatable = is_repeatable;
    }
}
