package com.mhealthkenya.psurvey.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsList {

    @SerializedName("Questions")
    private List<Questions> QuestionsList;

    public List<Questions> getQuestionsList() {
        return QuestionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        QuestionsList = questionsList;
    }

    List<Questions> quiz;
}
