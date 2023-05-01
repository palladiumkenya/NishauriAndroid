package com.mhealth.nishauri.Models;

public class ArtModel {
public  String code_art, name_art, facility_type, telephone;


    public ArtModel() {
    }

    public ArtModel(String code_art, String name_art, String facility_type, String telephone) {
        this.code_art = code_art;
        this.name_art = name_art;
        this.facility_type = facility_type;
        this.telephone = telephone;
    }

    public String getCode_art() {
        return code_art;
    }

    public void setCode_art(String code_art) {
        this.code_art = code_art;
    }

    public String getName_art() {
        return name_art;
    }

    public void setName_art(String name_art) {
        this.name_art = name_art;
    }

    public String getFacility_type() {
        return facility_type;
    }

    public void setFacility_type(String facility_type) {
        this.facility_type = facility_type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
