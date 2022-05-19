package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Answers {

    @PrimaryKey
    private int Answer_ID;
    private  String AnswerName;


    public Answers() {
    }

    public Answers(int answer_ID, String answerName) {
        Answer_ID = answer_ID;
        AnswerName = answerName;
    }

    public int getAnswer_ID() {
        return Answer_ID;
    }

    public void setAnswer_ID(int answer_ID) {
        Answer_ID = answer_ID;
    }

    public String getAnswerName() {
        return AnswerName;
    }

    public void setAnswerName(String answerName) {
        AnswerName = answerName;
    }
}
