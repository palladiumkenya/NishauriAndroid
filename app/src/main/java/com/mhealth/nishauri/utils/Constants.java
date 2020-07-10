package com.mhealth.nishauri.utils;

import com.mhealth.nishauri.Models.Dependant;

public class Constants {
    /*ENDPOINT*/
    public static String ENDPOINT = "http://nishauri-api.mhealthkenya.co.ke/api/";

    //AUTH
    public static String REGISTER = "users/";
    public static String LOGIN = "token/login/";

    //USER
    public static String CURRENT_USER = "user/auth";
    public static String UPDATE_USER = "user/update";

    //DEPENDANT
    public static String ADD_DEPENDANT = "dependants/";
    public static String DEPENTANTS= "dependants/";
    public static String DEPENTANT= "dependant/";
    public static String UPDATE_DEPENDANT = "dependant/update";

    //LAB
    public static String VIRAL_LOAD= "lab/vload";
    public static String EID= "lab/eid";

    //APPOINTMENT
    public static String UPCOMING_APPOINTMENT = "appointments/user/upcoming";
    public static String PASSED_APPOINTMENT = "appointments/user/past";
    public static String SCHEDULE_APPOINTMENT = "appointments/user/book";
    public static String RESCHEDULE_APPOINTMENT = "appointments/user/reschedule/";

    //TREATMENTS
    public static String CURENT_TREATMENTS = "user/treatment";
    public static String PASSED_TREATMENTS = "user/treatment";


    /*MODELS*/
    public static String AUTH_TOKEN = "";

}
