package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class Clientprofile extends SugarRecord {

    String marital,treatmentcat,texttime,language,smsenabled;

    public Clientprofile() {
    }

    public Clientprofile(String marital, String treatmentcat, String texttime, String language, String smsenabled) {
        this.marital = marital;
        this.treatmentcat = treatmentcat;
        this.texttime = texttime;
        this.language = language;
        this.smsenabled = smsenabled;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getTreatmentcat() {
        return treatmentcat;
    }

    public void setTreatmentcat(String treatmentcat) {
        this.treatmentcat = treatmentcat;
    }

    public String getTexttime() {
        return texttime;
    }

    public void setTexttime(String texttime) {
        this.texttime = texttime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSmsenabled() {
        return smsenabled;
    }

    public void setSmsenabled(String smsenabled) {
        this.smsenabled = smsenabled;
    }
}
