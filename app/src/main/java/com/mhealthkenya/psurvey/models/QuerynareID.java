package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class QuerynareID extends SugarRecord {

    long questinareID;

    public QuerynareID() {
    }

    public QuerynareID(long questinareID) {
        this.questinareID = questinareID;
    }

    public long getQuestinareID() {
        return questinareID;
    }

    public void setQuestinareID(long questinareID) {
        this.questinareID = questinareID;
    }
}
