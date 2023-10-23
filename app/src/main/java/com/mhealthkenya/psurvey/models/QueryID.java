package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class QueryID extends SugarRecord {

    long QuestnID;

    public QueryID() {
    }

    public QueryID(long questnID) {
        QuestnID = questnID;
    }

    public long getQuestnID() {
        return QuestnID;
    }

    public void setQuestnID(long questnID) {
        QuestnID = questnID;
    }
}
