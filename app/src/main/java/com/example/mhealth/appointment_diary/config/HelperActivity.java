package com.example.mhealth.appointment_diary.config;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;

public class HelperActivity extends Activity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.example.mhealth.appointment_diary", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            //strat  DataActivity beacuase its your app first run
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
            startActivity(new Intent(HelperActivity.this , SelectUrls.class));
            finish();
        }
        else {
            startActivity(new Intent(HelperActivity.this , LoginActivity.class));
            finish();
        }
    }
}
