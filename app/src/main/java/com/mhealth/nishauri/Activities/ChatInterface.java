package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.mhealth.nishauri.R;

public class ChatInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface);

        Toolbar toolbar = findViewById(R.id.toolbarMsg);

        setSupportActionBar(toolbar);
    }
}