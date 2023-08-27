package com.mhealthkenya.psurvey.models;

public class UploadModel {

    int id;
    String ccc_number;
    int mfl_code;
    boolean has_completed_survey;
    int questionnaire ;
  //  boolean has_completed_survey;


    public UploadModel() {
    }

    public UploadModel(int id, String ccc_number, int mfl_code, boolean has_completed_survey, int questionnaire) {
        this.id = id;
        this.ccc_number = ccc_number;
        this.mfl_code = mfl_code;
        this.has_completed_survey = has_completed_survey;
        this.questionnaire = questionnaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCcc_number() {
        return ccc_number;
    }

    public void setCcc_number(String ccc_number) {
        this.ccc_number = ccc_number;
    }

    public int getMfl_code() {
        return mfl_code;
    }

    public void setMfl_code(int mfl_code) {
        this.mfl_code = mfl_code;
    }

    public boolean isHas_completed_survey() {
        return has_completed_survey;
    }

    public void setHas_completed_survey(boolean has_completed_survey) {
        this.has_completed_survey = has_completed_survey;
    }

    public int getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(int questionnaire) {
        this.questionnaire = questionnaire;
    }
}
