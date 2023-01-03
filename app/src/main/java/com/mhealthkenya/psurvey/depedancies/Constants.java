package com.mhealthkenya.psurvey.depedancies;

public class Constants {


    //public static int dataID;

    /*ENDPOINT*/

  public static String ENDPOINT ="https://psurveyapi.kenyahmis.org/";

  //public static String ENDPOINT =" https://psurveyapitest.kenyahmis.org/";


    //AUTH
    public static String SIGNUP = "auth/users/";
    public static String CURRENT_USER = "auth/users/me";
    public static String CURRENT_USER_DETAILED = "api/current/user";
    public static String UPDATE_USER = "auth/users/me/";
    public static String LOGIN = "auth/token/login";
    public static String LOGOUT = "auth/token/logout/";



    //FACILITIES AND DESIGNATIONS
    public static String ALL_FACILITIES = "api/facilities";
    public static String DESIGNATION = "api/designation";



    //QUESTIONNAIRES
    public static String ACTIVE_SURVEYS = "api/questionnaire/active";
    public static String ALL_QUESTIONNAIRES = "api/questionnaire/all";
    public static String PATIENT_CONSENT = "api/questionnaire/start/";
    public static String PROVIDE_ANSWER = "api/questions/answer/";
    public static String INITIAL_CONFIRMATION = "api/initial/consent/";
    public static String GET_PARTICIPANTS ="api/questionnaire/participants/";


    /*MODELS*/
    public static String AUTH_TOKEN = "";


}
