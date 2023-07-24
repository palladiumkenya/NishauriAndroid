package com.mhealth.nishauri.Models;

public class auth {


    private String auth_token;

    public auth(String auth_token) {
        this.auth_token = auth_token;

    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

}
