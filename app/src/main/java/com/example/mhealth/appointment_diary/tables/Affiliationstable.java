package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Affiliationstable extends SugarRecord {

    String affiliationid,affiliationname;

    public Affiliationstable() {
    }

    public Affiliationstable(String affiliationid, String affiliationname) {
        this.affiliationid = affiliationid;
        this.affiliationname = affiliationname;
    }

    public String getAffiliationid() {
        return affiliationid;
    }

    public void setAffiliationid(String affiliationid) {
        this.affiliationid = affiliationid;
    }

    public String getAffiliationname() {
        return affiliationname;
    }

    public void setAffiliationname(String affiliationname) {
        this.affiliationname = affiliationname;
    }
}
