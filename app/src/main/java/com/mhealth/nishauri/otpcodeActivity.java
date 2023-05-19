package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class otpcodeActivity extends AppCompatActivity {
    Button btn_login1;
    EditText editText1, editText2, editText3, editText4, editText5;
    Toolbar toolbar1;
    private User loggedInUser;

    String userID11;
    int pageID11;
    String userExtra;

    String  errors1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);

        toolbar1 =findViewById(R.id.toolbarr);
        toolbar1.setTitle("Nishauri");
        setSupportActionBar(toolbar1);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userExtra = extras.getString("user_ID");
            // and get whatever type user account id is
        }

        //userID
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;






        btn_login1 =findViewById(R.id.btn_login);
        editText1 =findViewById(R.id.otp_edit_text1);
        editText2 = findViewById(R.id.otp_edit_text2);
        editText3= findViewById(R.id.otp_edit_text3);
        editText4 =findViewById(R.id.otp_edit_text4);
        editText5 =findViewById(R.id.otp_edit_text5);


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
                postOtp(auth_token, editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString()+editText5.getText().toString());
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
}