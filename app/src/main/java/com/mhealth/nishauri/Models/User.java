package com.mhealth.nishauri.Models;

public class User {
     String auth_token;






    public User(String auth_token) {
        this.auth_token = auth_token;

    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }


}
