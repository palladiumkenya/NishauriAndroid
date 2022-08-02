package com.example.mhealth.appointment_diary.config;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;

public class TryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);


        Toast.makeText(TryActivity.this, "ur"+URLActivity.BASE_URL, Toast.LENGTH_LONG).show();
    }
}