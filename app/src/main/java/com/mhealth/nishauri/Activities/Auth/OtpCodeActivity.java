package com.mhealth.nishauri.Activities.Auth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toolbar;

import com.mhealth.nishauri.R;

public class OtpCodeActivity extends AppCompatActivity {

    Toolbar toolbar1;

    EditText ed1, ed2, ed3, ed4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_code);

        ed1 =findViewById(R.id.otp_edit_text1);
        ed2 = findViewById(R.id.otp_edit_text2);
        ed3 =findViewById(R.id.otp_edit_text3);
        ed4 =findViewById(R.id.otp_edit_text4);

        //toolbar1 =findViewById(R.id.toolbar);
        //toolbar1.setTitle("Nishauri");
    }
}