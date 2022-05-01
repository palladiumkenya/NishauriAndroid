package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mhealthkenya.psurvey.R;

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