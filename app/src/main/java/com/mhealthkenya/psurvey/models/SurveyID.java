package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


@Table(name = "SurveyID")
public class SurveyID extends SugarRecord {

    int quetionereID;

    public SurveyID() {
    }

    public SurveyID(int quetionereID) {
        this.quetionereID = quetionereID;
    }

    public int getQuetionereID() {
        return quetionereID;
    }

    public void setQuetionereID(int quetionereID) {
        this.quetionereID = quetionereID;
    }
}
