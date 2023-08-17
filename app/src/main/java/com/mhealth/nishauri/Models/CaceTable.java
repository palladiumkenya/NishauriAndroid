package com.mhealth.nishauri.Models;

import com.orm.SugarRecord;

public class CaceTable extends SugarRecord {

    String CCCno;

    public CaceTable() {
    }

    public CaceTable(String CCCno) {
        this.CCCno = CCCno;
    }

    public String getCCCno() {
        return CCCno;
    }

    public void setCCCno(String CCCno) {
        this.CCCno = CCCno;
    }
}
