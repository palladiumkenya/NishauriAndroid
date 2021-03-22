package com.example.mhealth.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.loginmodule.Registration;

public class privecy extends AppCompatActivity {

    WebView webView;
    public String datafile = "privacy.html";

    Button accept1, decline1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privecy);

        accept1 = findViewById(R.id.accept);
        decline1 = findViewById(R.id.decline);

        webView =(WebView) findViewById(R.id.privacyId);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/" +datafile);


        accept1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =  new Intent(privecy.this, Registration.class);
                startActivity(intent1);
            }
        });

        decline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(privecy.this, LoginActivity.class);
                startActivity(intent2);
            }
        });



    }
}