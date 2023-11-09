package com.mhealthkenya.psurvey.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.orm.SugarRecord;

@Entity(tableName = "user_responses")
public class UserResponseEntity extends SugarRecord {

    @PrimaryKey(autoGenerate = true)
    private int idA;

    private int questionnaireId;
    private int questionId;
    private int answerId;
    private  String option;


    public UserResponseEntity() {
    }

    public UserResponseEntity(int questionnaireId, int questionId,  String option) {
        this.questionnaireId = questionnaireId;
        this.questionId = questionId;

        this.option = option;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }


    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
