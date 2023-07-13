package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.NewPassword;
import com.mhealth.nishauri.ProfileActivity;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.otpcodeActivity;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfileOTP extends AppCompatActivity {

   String cccExtra, upiExtra, firstExtra, phne;
    Button btn_sub;
    Toolbar toolbar;
    public User loggedInUser;
    EditText editText1, editText2, editText3, editText4, editText5;
    TextView otpphone1;

    String  errors1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_otp);
        toolbar =findViewById(R.id.toolbarr);

        toolbar.setTitle("Complete Profile");
        setSupportActionBar(toolbar);

        otpphone1 =findViewById(R.id.otpphone);


       // init();
        btn_sub = findViewById(R.id.bt_prof);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cccExtra = extras.getString("cc1");
            upiExtra = extras.getString("upi1");
            firstExtra = extras.getString("first1");
            phne = extras.getString("phoneA");
            // and get whatever type user account id is
        }
        otpphone1.setText("OTP Sent to"+ " "+phne);

        //userID
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
       // String urls =auth_token;

        btn_sub =findViewById(R.id.btn_logina);
        editText1 =findViewById(R.id.otp_edit_texta);
        editText2 = findViewById(R.id.otp_edit_textb);
        editText3= findViewById(R.id.otp_edit_textc);
        editText4 =findViewById(R.id.otp_edit_textd);
        editText5 =findViewById(R.id.otp_edit_texte);

        editText1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText1.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText2.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText3.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText4.getText().toString().length() == 1)     //size as per your requirement
                {
                    editText5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOtp(auth_token, cccExtra, upiExtra, firstExtra,editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString()+editText5.getText().toString());

            }
        });
    }
    public void postOtp(String userID, String ccc,String upi, String fname, String otp){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userID);
            jsonObject.put("ccc_no", ccc);
            jsonObject.put("upi_no", upi);
            jsonObject.put("firstname", fname);
            jsonObject.put("otp_number", otp);

           // Log.d("variables", jsonObject.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(Constants.ENDPOINT+ Constants.SET_program1)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                /*.setRetryPolicy(new DefaultRetryPolicy(
                        30000, //for 30 Seconds
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))*/

                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());


                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            errors1 = response.has("msg") ? response.getString("msg") : "" ;

                           // JSONObject jsonObject1 =response.getJSONObject("data");
                            //userID11 =jsonObject1.getString("user_id");
                           // pageID11 = jsonObject1.getInt("page_id");

                            if (status){
                                //Toast.makeText(ProfileOTP.this, errors1, Toast.LENGTH_SHORT).show();
                                Intent intent1 =new Intent(ProfileOTP.this, LoginActivity.class);
                               // intent1.putExtra("ID",  userID11);
                                startActivity(intent1);

                            }
                            else if(!status){

                                Toast.makeText(ProfileOTP.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(ProfileOTP.this, errors1, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(ProfileOTP.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(ProfileOTP.this, "Invalid OTP Code", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(ProfileOTP.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

    }
}