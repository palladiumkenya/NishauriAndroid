package com.mhealthkenya.psurvey.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Questions")
public class Questions {

    @PrimaryKey
    private  int Question_ID;
    private String QuestionName;
    private String QuestionOrder;
    private String QuestionType;
    private int questionnaire;



    public Questions() {
    }

    public Questions(int question_ID, String questionName, String questionOrder, String questionType) {
        Question_ID = question_ID;
        QuestionName = questionName;
        QuestionOrder = questionOrder;
        QuestionType = questionType;
       // this.questionnaire = questionnaire;
    }


    public int getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(int question_ID) {
        Question_ID = question_ID;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public String getQuestionOrder() {
        return QuestionOrder;
    }

    public void setQuestionOrder(String questionOrder) {
        QuestionOrder = questionOrder;
    }

    public String getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(String questionType) {
        QuestionType = questionType;
    }

    public int getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(int questionnaire) {
        this.questionnaire = questionnaire;
    }



}
