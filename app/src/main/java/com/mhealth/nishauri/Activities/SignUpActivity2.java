package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mhealth.nishauri.R;

public class SignUpActivity2 extends AppCompatActivity {

    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        registerBtn =findViewById(R.id.btn_login);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignUpActivity2.this, OtpActivity.class);
                startActivity(intent);
            }
        });


    }
}