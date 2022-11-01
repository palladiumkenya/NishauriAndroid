package com.example.mhealth.appointment_diary.tables;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


public class UrlTable extends SugarRecord {

    public String base_url1, stage_name1;

    public UrlTable() {
    }

    public UrlTable(String base_url1, String stage_name1) {
        this.base_url1 = base_url1;
        this.stage_name1 = stage_name1;
    }

    public String getBase_url1() {
        return base_url1;
    }

    public void setBase_url1(String base_url1) {
        this.base_url1 = base_url1;
    }

    public String getStage_name1() {
        return stage_name1;
    }

    public void setStage_name1(String stage_name1) {
        this.stage_name1 = stage_name1;
    }
}
