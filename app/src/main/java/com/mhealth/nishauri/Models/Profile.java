package com.mhealth.nishauri.Models;

public class Profile {

    private String ccc_no;
    private String security_question;
    private String security_answer;
    private String terms_accepted;
    private String id;
    private String msisdn;



    public Profile( String ccc_no, String security_question, String security_answer, String terms_accepted, String id, String msisdn) {

        this.ccc_no = ccc_no;
        this.security_question = security_question;
        this.security_answer = security_answer;
        this.terms_accepted = terms_accepted;
        this.id = id;
        this.msisdn = msisdn;

    }

    public String getCcc_no() {
        return ccc_no;
    }

    public void setCcc_no(String ccc_no) {
        this.ccc_no = ccc_no;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    public String getTerms_accepted() {
        return terms_accepted;
    }

    public void setTerms_accepted(String terms_accepted) {
        this.terms_accepted = terms_accepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
