package com.mhealth.nishauri.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mhealth.nishauri.R;

public class InformedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informed);


        WebView informedWeb = findViewById(R.id.informedWeb);

        WebSettings settings = informedWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        //Toast.makeText(InformedActivity.this, "inform", Toast.LENGTH_SHORT).show();
        informedWeb.loadUrl("https://psurvey-api.mhealthkenya.co.ke/api/informed");
    }
}