package com.mhealth.nishauri.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mhealth.nishauri.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        WebView informedWeb = findViewById(R.id.privacyWeb);

        WebSettings settings = informedWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        informedWeb.loadUrl("https://psurvey-api.mhealthkenya.co.ke/api/privacy");
    }
}