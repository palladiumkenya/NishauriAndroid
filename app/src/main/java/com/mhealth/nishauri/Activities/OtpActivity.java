package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mhealth.nishauri.LoginActivity2;
import com.mhealth.nishauri.R;

public class OtpActivity extends AppCompatActivity {

    Button sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this, LoginActivity2.class);
                startActivity(intent);
            }
        });
    }
}