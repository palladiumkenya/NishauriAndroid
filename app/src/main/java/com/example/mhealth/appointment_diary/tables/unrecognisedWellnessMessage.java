package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;

public class unrecognisedWellnessMessage extends SugarRecord {

    public String ccno,fname,phoneno,msg,msgid,removed;

    public unrecognisedWellnessMessage(String ccno, String fname, String phoneno, String msg, String msgid,String removed) {
        this.ccno = ccno;
        this.fname = fname;
        this.phoneno = phoneno;
        this.msg = msg;
        this.msgid = msgid;
        this.removed=removed;
    }

    public unrecognisedWellnessMessage() {
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
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
