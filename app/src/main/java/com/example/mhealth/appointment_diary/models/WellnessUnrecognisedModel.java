package com.example.mhealth.appointment_diary.models;

public class WellnessUnrecognisedModel {

    String ccno,fname,phoneno,msg,msgid;

    public WellnessUnrecognisedModel() {
    }

    public WellnessUnrecognisedModel(String ccno, String fname, String phoneno, String msg, String msgid) {
        this.ccno = ccno;
        this.fname = fname;
        this.phoneno = phoneno;
        this.msg = msg;
        this.msgid = msgid;
    }

    public String getCcno() {
        return ccno;
    }

    public void setCcno(String ccno) {
        this.ccno = ccno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
