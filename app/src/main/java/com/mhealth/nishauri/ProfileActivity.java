package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.Auth.SignUpActivity;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Activities.ProfileOTP;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity {

    EditText ccc, upi, first;
    EditText editText1, editText2, editText3, editText4, editText5;
    Button btn_sub, btn_sub1;
    Toolbar toolbar;
    String  errors1;

    CountDownTimer countDownTimer;

    LinearLayout linearLayout3;


    TextView countdownTextView;

    String userExtra;
    private User loggedInUser;
    String phonenoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
        linearLayout3 = findViewById(R.id.linearLayout3);
       // String urls2 =auth_token;
        init();
        btn_sub = findViewById(R.id.bt_prof);
        btn_sub1 =findViewById(R.id.submit_otp);
        Bundle extras = getIntent().getExtras();

       /* if (extras != null) {
            userExtra = extras.getString("user_ID");
            // and get whatever type user account id is
        }*/

        ccc= findViewById(R.id.ccc_no);
        upi = findViewById(R.id.upi_no);
        first = findViewById(R.id.f_name);

        editText1 =findViewById(R.id.otp_edit_texta);
        editText2 = findViewById(R.id.otp_edit_textb);
        editText3= findViewById(R.id.otp_edit_textc);
        editText4 =findViewById(R.id.otp_edit_textd);
        editText5 =findViewById(R.id.otp_edit_texte);
        countdownTextView = findViewById(R.id.countdownTextView);

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

        btn_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText1.getText().toString().isEmpty()) {

                } else if (editText2.getText().toString().isEmpty()){

                }else if (editText3.getText().toString().isEmpty()){

                }else if (editText4.getText().toString().isEmpty()){

                }else if (editText5.getText().toString().isEmpty()) {

                }else{


                postOtp(auth_token, ccc.getText().toString(), upi.getText().toString(), first.getText().toString(), editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString() + editText5.getText().toString());
            }
            }
        });



        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();

                if (ccc.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter CCC Number", Toast.LENGTH_SHORT).show();

                }else if(first.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();

                }

                else{


                send(auth_token, ccc.getText().toString(), upi.getText().toString(), first.getText().toString());}

            }
        });
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Set Program");



    }

    private void send(String userid, String cc, String up, String fname){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);
            jsonObject.put("ccc_no", cc);
            jsonObject.put("upi_no", up);
            jsonObject.put("firstname", fname);

            Log.d("profile", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



        AndroidNetworking.post(Constants.ENDPOINT+Constants.VALIDATE_program)
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
//                        Log.e(TAG, response.toString());+

                        //Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();


                        try {
                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  Message = response.has("msg") ? response.getString("msg") : "" ;


                            if (status){
                             JSONObject jsonObject1 = response.getJSONObject("data");
                                Toast.makeText(ProfileActivity.this, "Profile created"+ " "+Message, Toast.LENGTH_SHORT).show();
                                linearLayout3.setVisibility(View.VISIBLE);
                                btn_sub1.setVisibility(View.VISIBLE);


                                startCountdown();
                              /* phonenoA = jsonObject1.getString("phoneno");

                                Intent mint = new Intent(ProfileActivity.this, ProfileOTP.class);

                               // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mint.putExtra("cc1",  ccc.getText().toString());
                                mint.putExtra("upi1",  upi.getText().toString());
                                mint.putExtra("first1",  first.getText().toString());
                                mint.putExtra("phoneA",  phonenoA);
                                startActivity(mint); Toast.makeText(ProfileActivity.this, "Profile created"+ " "+Message, Toast.LENGTH_SHORT).show();*/

                            }
                            else if (!status){
                                linearLayout3.setVisibility(View.GONE);
                                btn_sub1.setVisibility(View.GONE);

                                Toast.makeText(ProfileActivity.this, Message, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());
                        linearLayout3.setVisibility(View.GONE);
                        btn_sub1.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();



                    }
                });

    }

    private void startCountdown() {
        // Hide the request OTP button
        btn_sub.setVisibility(View.GONE);

        // Show the countdown text view
        countdownTextView.setVisibility(View.VISIBLE);

        countDownTimer = new CountDownTimer(45000, 1000) { // 60 seconds countdown
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                countdownTextView.setVisibility(View.GONE);
                btn_sub.setVisibility(View.VISIBLE);
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
                                Intent intent1 =new Intent(ProfileActivity.this, LoginActivity.class);
                                // intent1.putExtra("ID",  userID11);
                                startActivity(intent1);

                            }
                            else if(!status){

                                Toast.makeText(ProfileActivity.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(ProfileActivity.this, errors1, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(ProfileActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(ProfileActivity.this, "Invalid OTP Code", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(ProfileActivity.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

    }
}