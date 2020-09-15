package com.mhealthkenya.psurvey.models;

public class Questionnaires {

    private int id;
    private String questionnaireTitle;
    private String questionnaireDescription;



    public Questionnaires(int id, String questionnaireTitle,String questionnaireDescription) {
        this.id = id;
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionnaireTitle() {
        return questionnaireTitle;
    }

    public void setQuestionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
    }

    public String getQuestionnaireDescription() {
        return questionnaireDescription;
    }

    public void setQuestionnaireDescription(String questionnaireDescription) {
        this.questionnaireDescription = questionnaireDescription;
    }


}
