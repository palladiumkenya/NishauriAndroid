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

public class Constants extends AppCompatActivity {
    /*ENDPOINT*/
    //public static String ENDPOINT = "https://nishauri-api.mhealthkenya.co.ke/api/";

   // https://nishauri-api.mhealthkenya.co.ke/api/lab/vload

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
    public static String SET_program="nishauri/setprogram";



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
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constants);

        TextView x =findViewById(R.id.show);
        //Button xx =findViewById(R.id.show1);

        getAlert();
    }
    private void getAlert(){

        try {


            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    zz=_url.get(x).getStage_name1();
                    //Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }



        AlertDialog.Builder builder1 = new AlertDialog.Builder(Constants.this);
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setTitle("You are connected to");
        builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Constants.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        //dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
