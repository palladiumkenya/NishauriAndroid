package com.mhealth.nishauri.Models;

public class CurrentArt {

   /* private String firstname;
    private String middlename;
    private String lastnamed;
    private String ccc_no;*/
    private String regimen;

    public CurrentArt() {
    }

    public CurrentArt(String regimen) {
        this.regimen = regimen;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }
}
