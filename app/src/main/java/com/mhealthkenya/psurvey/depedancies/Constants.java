package com.mhealthkenya.psurvey.depedancies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.SelectUrls;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.models.UrlTable;

import java.util.List;

public class Constants extends AppCompatActivity {
  public String z, zz;


  //public static int dataID;

  /*ENDPOINT*/

 // public static String ENDPOINT = "https://psurveyapi.kenyahmis.org/";

  //public static String ENDPOINT =" https://psurveyapitest.kenyahmis.org/";


  //AUTH
  public static String SIGNUP = "/auth/users/";
  public static String CURRENT_USER = "/auth/users/me";
  public static String CURRENT_USER_DETAILED = "/api/current/user";
  public static String UPDATE_USER = "/auth/users/me/";
  public static String LOGIN = "/auth/token/login";
  public static String LOGOUT = "/auth/token/logout/";


  //FACILITIES AND DESIGNATIONS
  public static String ALL_FACILITIES = "/api/facilities";
  public static String DESIGNATION = "/api/designation";


  //QUESTIONNAIRES
  public static String ACTIVE_SURVEYS = "/api/questionnaire/active";
  public static String ALL_QUESTIONNAIRES = "/api/questionnaire/all";
  public static String PATIENT_CONSENT = "/api/questionnaire/start/";
  public static String PROVIDE_ANSWER = "/api/questions/answer/";
  public static String INITIAL_CONFIRMATION = "/api/initial/consent/";
  public static String GET_PARTICIPANTS = "/api/questionnaire/participants/";


  /*MODELS*/
  public static String AUTH_TOKEN = "";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_constant);
    getAlert();

  }

  private void getAlert() {

    try {


      List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
      if (_url.size() == 1) {
        for (int x = 0; x < _url.size(); x++) {
          z = _url.get(x).getBase_url1();
          zz = _url.get(x).getStage_name1();
          //Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
        }
      }
    } catch (Exception e) {
      Log.d("No baseURL", e.getMessage());
    }


    AlertDialog.Builder builder1 = new AlertDialog.Builder(Constants.this);
    builder1.setIcon(R.drawable.logo);
    builder1.setTitle("You are connected to");
    builder1.setMessage(zz);
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
   /* builder1.setNegativeButton(
            "Cancel",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(Constants.this, SelectUrls.class);
                startActivity(intent);
                //dialog.cancel();
              }
            });*/
    AlertDialog alert11 = builder1.create();
    alert11.show();

  }
}