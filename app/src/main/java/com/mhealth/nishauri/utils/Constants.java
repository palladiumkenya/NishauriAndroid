package com.mhealth.nishauri.utils;



public class Constants {
    /*ENDPOINT*/
    public static String ENDPOINT = "https://nishauri-api.mhealthkenya.co.ke/api/";
   // https://nishauri-api.mhealthkenya.co.ke/api/lab/vload

    //AUTH
    public static String REGISTER = "signup/";
    public static String LOGIN = "token/login/";
    public static String DASHBOARD = "auth/dashboard";

    //USER
    public static String CURRENT_USER = "user/auth";
    public static String UPDATE_USER = "user/update";
    public static String UPDATE_REGIMEN = "user/regiment";


    //DEPENDANT
    public static String ADD_DEPENDANT = "dependants/";
    public static String DEPENTANTS= "dependants/";
    public static String DEPENTANT= "dependant/single";
    public static String UPDATE_DEPENDANT = "dependant/update";

    //LAB
    public static String VIRAL_LOAD= "lab/vload";
    public static String EID= "lab/eid";

    //APPOINTMENT
    public static String UPCOMING_APPOINTMENT = "appointments/user/upcoming";
    public static String PENDING_APPOINTMENT = "appointments/user/book";
    public static String PASSED_APPOINTMENT = "appointments/user/past";
    public static String SCHEDULE_APPOINTMENT = "appointments/user/book";
    public static String RESCHEDULE_APPOINTMENT = "appointments/user/reschedule/";
    public static String CONFIRM_APPOINTMENT = "appointments/user/accept/";

    //TREATMENTS
    public static String CURRENT_REGIMEN = "user/regiment";
    public static String PREVIOUS_REGIMEN = "user/regiment";

    //FACILITIES
    public static String ALL_FACILITIES = "facilities/all ";


    /*MODELS*/
    public static String AUTH_TOKEN = "";

}
