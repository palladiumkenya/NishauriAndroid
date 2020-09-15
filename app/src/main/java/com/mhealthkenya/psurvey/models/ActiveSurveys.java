package com.mhealthkenya.psurvey.models;

public class ActiveSurveys {

    private int id;
    private String surveyTitle;
    private String surveyDescription;



    public ActiveSurveys(int id, String surveyTitle,String surveyDescription) {
        this.id = id;
        this.surveyTitle = surveyTitle;
        this.surveyDescription = surveyDescription;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String questionnaireTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }


}

