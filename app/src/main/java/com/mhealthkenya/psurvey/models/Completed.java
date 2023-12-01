package com.mhealthkenya.psurvey.models;

import com.orm.SugarRecord;

public class Completed extends SugarRecord {

  public  int complete;

    public Completed() {
    }

    public Completed(int complete) {
        this.complete = complete;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
