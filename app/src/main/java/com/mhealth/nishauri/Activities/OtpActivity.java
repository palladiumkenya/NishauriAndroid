package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mhealth.nishauri.LoginActivity2;
import com.mhealth.nishauri.R;

public class OtpActivity extends AppCompatActivity {

    Button sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sendbtn =findViewById(R.id.btn_login11);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(OtpActivity.this, "To dashboard with client's data", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(OtpActivity.this, PatientActivity.class);
                startActivity(intent);
            }
        });
    }
}