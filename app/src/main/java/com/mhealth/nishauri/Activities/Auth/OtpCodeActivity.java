package com.mhealth.nishauri.Activities.Auth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toolbar;

import com.mhealth.nishauri.R;

public class OtpCodeActivity extends AppCompatActivity {

    Toolbar toolbar1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code);

        //toolbar1 =findViewById(R.id.toolbar);
        //toolbar1.setTitle("Nishauri");
    }
}