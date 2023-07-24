package com.mhealth.nishauri.Models;

import java.io.Serializable;

public class ActiveSurveys implements Serializable {

    private int id;
    private String name;
    private String description;
    private String is_active;
    private String created_at;
    private String active_till;
    private int created_by;
    public boolean expanded = false;
    public boolean parent = false;



    public ActiveSurveys(int id, String name,String description,String is_active,String created_at, String active_till, int created_by) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.is_active = is_active;
        this.created_at = created_at;
        this.active_till = active_till;
        this.created_by = created_by;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getActive_till() {
        return active_till;
    }

    public void setActive_till(String active_till) {
        this.active_till = active_till;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }


}
