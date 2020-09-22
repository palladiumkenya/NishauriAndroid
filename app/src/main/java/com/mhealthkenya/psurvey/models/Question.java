package com.mhealthkenya.psurvey.models;

import java.io.Serializable;

public class Question implements Serializable {

    private int id;
    private String question;
    private int question_type;
    private String createdAt;
    private int questionnaire;
    private int created_by;

    public Question(int id, String question, int question_type, String createdAt, int questionnaire, int created_by){

        this.id = id;
        this.question = question;
        this.createdAt = createdAt;
        this.question_type = question_type;
        this.questionnaire = questionnaire;
        this.created_by = created_by;

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


}
