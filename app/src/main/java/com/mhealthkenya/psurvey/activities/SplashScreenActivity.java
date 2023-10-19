package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    //startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    startActivity(new Intent(SplashScreenActivity.this, QuestionsOffline.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}