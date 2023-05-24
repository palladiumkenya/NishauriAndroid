package com.mhealth.nishauri.utils;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.R;

import java.util.List;

public class Constants{
    /*ENDPOINT*/

    //test
    public static String ENDPOINT = "https://ushauriapi.kenyahmis.org/";

    //live
  // public static String ENDPOINT ="https://ushauriapi.nascop.org/";



    //AUTH
    public static String REGISTER = "nishauri/signup/";

    public static String SIGNUP="nishauri/signup";
    public static String LOGIN = "nishauri/token/login/";
    public static String DASHBOARD = "nishauri/auth/dashboard";

    public static String SIGNIN = "nishauri/signin";
    public static String RESCHEDULE ="nishauri/reschedule";
    public static String APPOINTMENT_TRENDS="nishauri/appointment_trends";
    public static String APPOINTMENTS_MISSED= "nishauri/appointment_missed";

    public static String CURRENT_APPT="nishauri/current_appt";

    public static String UPDATE_Pwd="nishauri/updatepassword";
    public static String VERIFY_otp="nishauri/verifyotp";
    public static String RESET_pwd="nishauri/resetpassword";
    public static String SET_program1="nishauri/setprogram";

    public static String VALIDATE_program="nishauri/validate_program";



    //USER
    public static String CURRENT_USER = "nishauri/user/auth";
    public static String UPDATE_USER = "nishauri/user/update";
    public static String UPDATE_REGIMEN = "nishauri/user/regiment";
    public static String CURR_REGIMEN ="nishauri/regimen";
    public static String ART_dir ="nishauri/artdirectory";


    //Chat
    public static String CHAT="nishauri/chat";

    //DEPENDANT
    public static String ADD_DEPENDANT = "nishauri/dependants/";
    public static String DEPENTANTS= "nishauri/dependants/";
    public static String DEPENTANT= "nishauri/dependant/single";
    public static String UPDATE_DEPENDANT = "nishauri/dependant/update";
    public static String DEPENTANTS1="nishauri/dependants";

    //LAB
    public static String VIRAL_LOAD= "nishauri/lab/vload";
    public static String VIRALS_LOADNEW= "nishauri/vl_results";
    public static String EID= "nishauri/lab/eid";

    //APPOINTMENT
    public static String UPCOMING_APPOINTMENT = "nishauri/appointments/user/upcoming";
    public static String PENDING_APPOINTMENT = "nishauri/appointments/user/book";
    public static String PASSED_APPOINTMENT = "nishauri/appointments/user/past";
    public static String PASSED_APPOINTMENTNEW = "nishauri/appointment_previous";
   // nishauri/appointment_previous

    public static String SCHEDULE_APPOINTMENT = "nishauri/appointments/user/book";
    public static String RESCHEDULE_APPOINTMENT = "nishauri/appointments/user/reschedule/";
    public static String CONFIRM_APPOINTMENT = "nishauri/appointments/user/accept/";
    public static String PROFILE ="nishauri/profile";

    //TREATMENTS
    public static String CURRENT_REGIMEN = "nishauri/user/regiment";
    public static String PREVIOUS_REGIMEN = "nishauri/user/regiment";

    //FACILITIES
    public static String ALL_FACILITIES = "nishauri/facilities/all ";

    //BMI
    public static String BMI = "nishauri/bmi_calculator";




    /*MODELS*/
    public static String AUTH_TOKEN = "";
    public String z, zz;








}
