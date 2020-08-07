package com.mhealth.nishauri.Models;

public class Facility {
    private int id;
    private String mfl_code;
    private String name;
    private String county;
    private String sub_county;

    public Facility(int id, String mfl_code, String name, String county, String sub_county) {
        this.id = id;
        this.mfl_code = mfl_code;
        this.name = name;
        this.county = county;
        this.sub_county = sub_county;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMfl_code() {
        return mfl_code;
    }

    public void setMfl_code(String mfl_code) {
        this.mfl_code = mfl_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSub_county() {
        return sub_county;
    }

    public void setSub_county(String sub_county) {
        this.sub_county = sub_county;
    }


    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof Facility)) {
            return false;
        }
        Facility f = (Facility) anotherObject;
        return (this.id == f.id);
    }
}

