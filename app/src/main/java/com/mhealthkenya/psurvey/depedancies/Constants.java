package com.mhealthkenya.psurvey.depedancies;

public class Constants {

    /*ENDPOINT*/
    public static String ENDPOINT = "http://psurvey-api.mhealthkenya.co.ke/";

    //AUTH
    public static String SIGNUP = "auth/users/";
    public static String LOGIN = "auth/token/login";
    public static String CURRENT_USER = "auth/users/me";
    public static String UPDATE_USER = "auth/users/me/";
    public static String LOGOUT = "auth/token/logout/";



    //FACILITITES
    public static String ALL_FACILITIES = "api/facilities";



    //DESIGNATION
    public static String DESIGNATION = "api/designation";


    //QUESTIONNAIRES
    public static String ACTIVE_SURVEYS = "api/questionnaire/active";
    public static String ALL_QUESTIONNAIRES = "api/questionnaire/all";




    /*MODELS*/
    public static String AUTH_TOKEN = "";


}
