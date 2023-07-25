package com.mhealth.nishauri.utils;

import static android.R.layout.simple_spinner_dropdown_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.ActiveSurveys;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LastConsent extends AppCompatActivity {
    public auth loggedInUser;
     ActiveSurveys activeSurveys;
    public boolean informb, privacyb, stateb;
    int dataID=0;


    MaterialTextView tv_chosen_survey_title;
    MaterialTextView tv_chosen_survey_introduction;
    Button btn_patient_consent;
    MaterialTextView tv_patient_name;
    MaterialTextView tv_patient_number;
    MaterialTextView patient_id;
    MaterialTextView tv_survey_id;


    TextView informText1;
    TextView privacyText1;


    CheckBox checkInform;
    CheckBox checkPrivacy;
    CheckBox checkStnt;

    //start survey


    TextInputLayout til_ccc_no;
    TextInputEditText etxt_ccc_no;
    TextInputLayout til_first_name;
    TextInputEditText etxt_first_name;

    //String activeSurveys2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_consent);

         tv_chosen_survey_title= findViewById(R.id.tv_chosen_survey_title);
         tv_chosen_survey_introduction = findViewById(R.id.tv_chosen_survey_introduction);
         btn_patient_consent= findViewById(R.id.btn_patient_consent);

        tv_patient_name= findViewById(R.id.tv_patient_name);
        tv_patient_number= findViewById(R.id.tv_patient_number);
        patient_id= findViewById(R.id.patient_id);
        tv_survey_id= findViewById(R.id.tv_survey_id);

        informText1= findViewById(R.id.informText);
        privacyText1= findViewById(R.id.privacyText);

        checkInform = findViewById(R.id.checkInform);
        checkPrivacy = findViewById(R.id.checkPrivacy);
        checkStnt= findViewById(R.id.checkStnt);

        til_ccc_no =findViewById(R.id.til_ccc_no);
        etxt_ccc_no=findViewById(R.id.etxt_ccc_no);
        til_first_name=findViewById(R.id.til_f_name);
        etxt_first_name=findViewById(R.id.etxt_first_name);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN2, auth.class);

       /* Intent intent = getIntent();
        activeSurveys2 =intent.getStringExtra("questionnaire");*/

        // getting the bundle back from the android
        Bundle bundle = getIntent().getExtras();
// getting the string back
        //activeSurveys2 = bundle.getString("questionnaire", "Default");

        //activeSurveys = (ActiveSurveys) bundle.getSerializable("questionnaire");
        activeSurveys = (ActiveSurveys) bundle.getSerializable("questionnaire");

        //Log.d("Active Surveys", String.valueOf(activeSurveys));
        //activeSurveys2=intent.getStringExtra("questionnaire");


        //tv_survey_id.setText("Questioonaire ID: "+String.valueOf(activeSurveys.getI));
        //activeSurveys = (ActiveSurveys) getArguments().getSerializable("questionnaire");



        informText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastConsent.this, InformedActivity.class);
                startActivity(intent);

            }
        });

        privacyText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LastConsent.this, PrivacyActivity.class);
                startActivity(intent);

            }
        });

//checkboxes

        if(checkInform.isChecked())
        {
            informb= Boolean.parseBoolean(checkInform.getText().toString());
        }


        if(checkPrivacy.isChecked())
        {
            //description=checkPrivacy.getText().toString();
            privacyb = Boolean.parseBoolean(checkPrivacy.getText().toString());

        }

        if(checkStnt.isChecked())
        {
            stateb = Boolean.parseBoolean(checkStnt.getText().toString());
        }
        //end checkboxes

        if (activeSurveys == null){
            Toast.makeText(LastConsent.this, "No Surveys found", Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(LastConsent.this, "Surveys found", Toast.LENGTH_LONG).show();
            tv_chosen_survey_title.setText(activeSurveys.getName());
            tv_chosen_survey_introduction.setText(activeSurveys.getDescription());
        }

        btn_patient_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInform.isChecked() || !checkPrivacy.isChecked() || !checkStnt.isChecked())
                {
                    Toast.makeText(LastConsent.this, "Please consent first", Toast.LENGTH_SHORT).show();


                }else {


                    confirmConsent(activeSurveys.getId(),etxt_ccc_no.getText().toString(), etxt_first_name.getText().toString(), 2);}
            }
        });
    }
    private void confirmConsent(int questionnaireId, String ccc_no, String firstName, int dataID) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("questionnaire_id", questionnaireId );
            jsonObject.put("ccc_number", ccc_no);
            jsonObject.put("first_name", firstName);

            jsonObject.put("questionnaire_participant_id", dataID);
            jsonObject.put("interviewer_statement", stateb);
            jsonObject.put("informed_consent", informb);
            jsonObject.put("privacy_policy", privacyb);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        String auth_token = loggedInUser.getAuth_token();
        AndroidNetworking.post("https://psurveyapitest.kenyahmis.org/api/questionnaire/start/")
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        Log.e("Success", response.toString());

                        try {

                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  message = response.has("message") ? response.getString("message") : "" ;


                            if (response.has("link")){

                                String link = response.has("link") ? response.getString("link") : "";
                                int sessionId = response.has("session") ? response.getInt("session") : 0;

                               /* Bundle bundle = new Bundle();
                                bundle.putString("questionLink",link);
                                bundle.putInt("sessionID",sessionId);*/

                                // bundle.putInt("questionnaire_id", questionnaireId);
                                //Navigation.findNavController(root).navigate(R.id.nav_questions, bundle);
                                Intent intent = new Intent(LastConsent.this, QuestionsActivity.class);
                                intent.putExtra("questionLink",link);
                                intent.putExtra("sessionID",sessionId);
                                startActivity(intent);



                                //Intent intent = new Intent(context, SingleQuestions.class);
                                //startActivity(intent);


                            }
                            else if (message.contains("client verification failed")){
                                Intent intent = new Intent(LastConsent.this, HomeActivitySurvey.class);
                                startActivity(intent);

                              //  Navigation.findNavController(root).navigate(R.id.nav_home);
                                Toast.makeText(LastConsent.this, message, Toast.LENGTH_SHORT).show();
                            }
                            else{

                                // Snackbar.make(root.findViewById(R.id.lastConsent2), errors, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("Error", error.getErrorDetail());


                        if (error.getErrorBody().contains("No questions")){
                            Toast.makeText(LastConsent.this, "No questions found for this survey!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(LastConsent.this, "Error: "+error.getErrorBody(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}