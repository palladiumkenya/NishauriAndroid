package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mhealth.nishauri.Activities.SignUpActivity2;

public class LoginActivity2 extends AppCompatActivity {
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        signup =findViewById(R.id.txt_sign_up1);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity2.this, SignUpActivity2.class);
                startActivity(intent);
            }
        });
    }
}