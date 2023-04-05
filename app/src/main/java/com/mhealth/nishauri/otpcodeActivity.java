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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class otpcodeActivity extends AppCompatActivity {
    Button btn_login1;
    EditText editText1, editText2, editText3, editText4, editText5;

    String userID11;
    int pageID11;
    String userExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userExtra = extras.getString("user_id");
            // and get whatever type user account id is
        }





        btn_login1 =findViewById(R.id.btn_login);
        editText1 =findViewById(R.id.otp_edit_text1);
        editText2 = findViewById(R.id.otp_edit_text2);
        editText3= findViewById(R.id.otp_edit_text3);
        editText4 =findViewById(R.id.otp_edit_text4);
        editText5 =findViewById(R.id.otp_edit_text5);;

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

            jsonObject.put("user_id", userID);
            jsonObject.put("otp", otp);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/verifyotp")
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
                            String  errors1 = response.has("msg") ? response.getString("msg") : "" ;

                            JSONObject jsonObject1 =response.getJSONObject("data");
                           userID11 =jsonObject1.getString("user_id");
                            pageID11 = jsonObject1.getInt("page_id");

                            String encryptedID11 = Base64Encoder.encryptString(userID11);


                            if (status){
                                Intent intent1 =new Intent(otpcodeActivity.this, NewPassword.class);
                                intent1.putExtra("", encryptedID11);
                                startActivity(intent1);

                            }
                            else{

                                Toast.makeText(otpcodeActivity.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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