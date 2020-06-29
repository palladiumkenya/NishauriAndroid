package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONObject;
import org.json.JSONException;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Toolbar toolbar;
    private TextView sign_up;
    private EditText phone;
    private EditText password;

    private TextInputLayout til_phone_no;
    private TextInputLayout til_password;
    private LottieAnimationView animationView;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stash.init(this);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);

//        initialization of components
        btn_login = findViewById(R.id.btn_login);
        sign_up = (TextView) findViewById(R.id.txt_sign_up);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        til_phone_no = findViewById(R.id.til_phone_no);
        til_password = findViewById(R.id.til_pass);
        animationView = findViewById(R.id.animationView);



        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int a, int a1, int a2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int a, int a1, int a2) {

                if (phone.length() < 10) {
                    til_phone_no.setError("Complete your phone number");
                } else {
                    til_phone_no.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int b, int b1, int b2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int b, int b1, int b2) {
                if (password.length() < 8) {
                    til_password.setError("Password should be 8 characters long");
                } else {
                    til_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(mint);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (TextUtils.isEmpty(phone.getText().toString())){
                   Snackbar.make(findViewById(R.id.login_layout), "Please enter your phone number", Snackbar.LENGTH_LONG).show();
               }
               else if (TextUtils.isEmpty(password.getText().toString())) {
                   Snackbar.make(findViewById(R.id.login_layout), "Please enter your password", Snackbar.LENGTH_LONG).show();
               }
               else {
                   sendLoginRequest(password.getText().toString(),phone.getText().toString());
                   animationView.setVisibility(View.VISIBLE);



               }


            }
        });


    }


    private void sendLoginRequest(String password, String phone) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("msisdn", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+Constants.LOGIN)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());

                        try {
                            String auth_token = response.has("auth_token") ? response.getString("auth_token") : "";
                            User newUser = new User(auth_token);

                            Stash.put(Constants.AUTH_TOKEN, newUser);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        animationView.setVisibility(View.GONE);

                        if (response.has("auth_token")){

                            Intent mint = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mint);

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        animationView.setVisibility(View.GONE);


                        Snackbar.make(findViewById(R.id.login_layout), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });



    }



}
