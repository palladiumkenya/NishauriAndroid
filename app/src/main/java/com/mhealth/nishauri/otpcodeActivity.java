package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class otpcodeActivity extends AppCompatActivity {


    CountDownTimer countDownTimer;
    TextView countdownTextView, Resend;



    Button btn_login1;
    EditText editText1, editText2, editText3, editText4, editText5;
    Toolbar toolbar1;
   // User loggedInUser;
    User loggedInUser;


    String userID11;
    int pageID11;
    String userExtra, phoneExtra;

    String  errors1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);

       //loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        //String auth_token = loggedInUser.getAuth_token();

        toolbar1 =findViewById(R.id.toolbarr);
        toolbar1.setTitle("Nishauri");
        countdownTextView=findViewById(R.id.countdownTextView);
        Resend=findViewById(R.id.Resend);
        setSupportActionBar(toolbar1);


        startCountdown();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userExtra = extras.getString("user_ID");
            phoneExtra =extras.getString("phone_no");
            // and get whatever type user account id is
        }


        btn_login1 =findViewById(R.id.btn_login);
        editText1 =findViewById(R.id.otp_edit_text1);
        editText2 = findViewById(R.id.otp_edit_text2);
        editText3= findViewById(R.id.otp_edit_text3);
        editText4 =findViewById(R.id.otp_edit_text4);
        editText5 =findViewById(R.id.otp_edit_text5);


        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getOtp();
                postReset(phoneExtra);
                startCountdown();
            }
        });

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




        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOtp(userExtra, editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString()+editText5.getText().toString());
                /*Intent intent1 =new Intent(otpcodeActivity.this, NewPassword.class);
                startActivity(intent1);*/
            }
        });
    }

    public void postOtp(String userID, String otp){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("otp", otp);
            jsonObject.put("user_id", userID);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(Constants.ENDPOINT+ Constants.VERIFY_otp)
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

                            JSONObject jsonObject1 =response.getJSONObject("data");
                           userID11 =jsonObject1.getString("user_id");
                            pageID11 = jsonObject1.getInt("page_id");

                            if (status){
                                Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_SHORT).show();
                                Intent intent1 =new Intent(otpcodeActivity.this, NewPassword.class);
                                intent1.putExtra("ID",  userID11);
                                startActivity(intent1);

                            }
                            else{

                                Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError error) {

                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(otpcodeActivity.this, "Invalid OTP Code", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(otpcodeActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }
                    }
               });

    }
    private void startCountdown() {
        // Hide the request OTP button
       Resend.setVisibility(View.GONE);

        // Show the countdown text view
        countdownTextView.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(45000, 1000) { // 60 seconds countdown
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                countdownTextView.setVisibility(View.GONE);
                Resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the countdown timer if the activity is destroyed
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }



    public void postReset(String userID){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("user_name", userID);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(Constants.ENDPOINT+ Constants.RESET_pwd)
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

                        // animationView.setVisibility(View.GONE);

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            errors1 = response.has("msg") ? response.getString("msg") : "" ;

                            JSONObject jsonObject1 =response.getJSONObject("data");
                      /*      userID1 =jsonObject1.getString("user_id");
                            page = jsonObject1.getInt("page_id");*/

                            // String encryptedID1 = Base64Encoder.encryptString(userID1);



                            if (status){


                               /* Intent intent1 =new Intent(PasswordReset.this, otpcodeActivity.class);
                                intent1.putExtra("user_ID", userID1);
                                intent1.putExtra("phone_no", etxt_email1.getText().toString());
                                startActivity(intent1);*/
                                Toast.makeText(otpcodeActivity.this, "OTP sent to"+ " "+phoneExtra, Toast.LENGTH_LONG).show();

                            }
                            else {

                                Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        //animationView.setVisibility(View.GONE);

                        // Snackbar.make(findViewById(R.id.signup_layout), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        //JSONObject jsonObject = new JSONObject();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(otpcodeActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();
                        }else {

                            Toast.makeText(otpcodeActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });


    }



}