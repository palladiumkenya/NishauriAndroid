package com.mhealthkenya.psurvey.models;

public class Dependancy {

    int Dependancy_ID;
    int Answer_ID;
    String Question_ID;

    public Dependancy(int dependancy_ID, int answer_ID, String question_ID) {
        Dependancy_ID = dependancy_ID;
        Answer_ID = answer_ID;
        Question_ID = question_ID;
    }

    public int getDependancy_ID() {
        return Dependancy_ID;
    }

    public void setDependancy_ID(int dependancy_ID) {
        Dependancy_ID = dependancy_ID;
    }

    public int getAnswer_ID() {
        return Answer_ID;
    }

    public void setAnswer_ID(int answer_ID) {
        Answer_ID = answer_ID;
    }

    public String getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(String question_ID) {
        Question_ID = question_ID;
    }
}
