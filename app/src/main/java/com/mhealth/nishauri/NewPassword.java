package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class NewPassword extends AppCompatActivity {
    EditText pwd11, pwd22;
            Button btn_login1;

    String  userID11;
    int page11;
    String  errors11;

    String  userExtra1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userExtra1 = extras.getString("ID");
            // and get whatever type user account id is
        }


        btn_login1= findViewById(R.id.btn_login);

        pwd11=findViewById(R.id.pwd1);
        pwd22=findViewById(R.id.pwd2);

        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postPwd(pwd11.getText().toString(), pwd22.getText().toString(), userExtra1);

            }
        });

    }

    public void postPwd(String pwdA,String pwdB, String userIDA){
        JSONObject jsonObject = new JSONObject();
        try {
            // jsonObject.put("CCCNo", ccc_no);
            jsonObject.put("password", pwdA);
            jsonObject.put("re_password", pwdB);
            jsonObject.put("user_id", userIDA);
            //jsonObject.put("securityQuestion", security_question);
            //jsonObject.put("securityAnswer", security_answer);
            /*jsonObject.put("msisdn", msisdn);
            jsonObject.put("password", password);
            jsonObject.put("re_password", repassword);
            jsonObject.put("termsAccepted", consent.isChecked() ? "true" : "false");*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/updatepassword")
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
                            errors11 = response.has("msg") ? response.getString("msg") : "" ;

                            JSONObject jsonObject1 =response.getJSONObject("data");
                            userID11 =jsonObject1.getString("user_id");
                            page11 = jsonObject1.getInt("page_id");

                            // String encryptedID1 = Base64Encoder.encryptString(userID1);



                            if (status){


                                Intent intent1 =new Intent(NewPassword.this, LoginActivity.class);
                                //intent1.putExtra("user_ID", userID11);
                                startActivity(intent1);

                            }
                            else {

                                Toast.makeText(NewPassword.this, errors11, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(NewPassword.this, errors11, Toast.LENGTH_LONG).show();

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
                            Toast.makeText(NewPassword.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();
                        }else {

                            Toast.makeText(NewPassword.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });


    }
}