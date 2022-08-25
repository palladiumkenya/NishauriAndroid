package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.LoginActivity2;
import com.mhealth.nishauri.R;

/*CREATED BY HUGH*/

public class
SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    startActivity(new Intent(SplashActivity.this, LoginActivity2.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
